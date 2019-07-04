package info.miguelcatalan.flyme.presentation.scale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ScaleLayoutBinding
import info.miguelcatalan.flyme.domain.airport.Location
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import kotlinx.android.synthetic.main.scale_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ScaleActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val KEY_ITINERARY = "key_itinerary"
        const val MAP_MARKERS_PADDING_PERCENTAGE = 0.20
        const val MAP_MARKERS_VERTICAL_PADDING_PERCENTAGE = 1.40
    }

    private lateinit var map: GoogleMap

    private val scaleViewModel: ScaleViewModel by viewModel { parametersOf(this@ScaleActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initBinding() {
        val scaleLayoutBinding: ScaleLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.scale_layout)

        scaleLayoutBinding.apply {
            lifecycleOwner = this@ScaleActivity
            viewModel = scaleViewModel
        }
    }

    private fun initViews() {
        scaleViewModel.itineray.observe(this, Observer {
            itineraryItem.setItinerary(it)
            val locationMarkers = getLocationMarkers(it)
            drawMarkers(locationMarkers)
            drawPolyline(locationMarkers)
            setZoomBasedOnMarkers(locationMarkers)
        })
    }

    private fun getLocationMarkers(itinerary: Itinerary): List<Location> {
        val locationList: MutableList<Location> = mutableListOf()
        itinerary.scales.forEach {
            it.departure.airport.location?.let { location ->
                locationList.add(location)
            }
        }
        itinerary.scales.last().arrival.airport.location?.let { location ->
            locationList.add(location)
        }
        return locationList
    }


    private fun drawPolyline(locationMarkers: List<Location>) {
        val polyLineOptions = PolylineOptions()
        polyLineOptions.width(7f)
        polyLineOptions.geodesic(true)
        polyLineOptions.color(resources.getColor(R.color.steel))
        locationMarkers.forEach {
            polyLineOptions.add(it.toLatLong())
        }
        polyLineOptions.pattern(
            listOf(
                Gap(20f),
                Dash(20f)
            )
        )

        val polyline = map.addPolyline(polyLineOptions)
        polyline.isGeodesic = true
    }

    private fun drawMarkers(locationMarkers: List<Location>) {
        locationMarkers.forEachIndexed { index, location ->
            val marker = MarkerOptions()
                .position(location.toLatLong())
                .icon(getIconByIndex(locationMarkers.size, index))
            map.addMarker(marker)
        }
    }

    private fun getIconByIndex(totalItems: Int, currentIntemIndex: Int): BitmapDescriptor {

        return when (currentIntemIndex) {
            0, (totalItems - 1) -> BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_relevant)
            else -> BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_scales)
        }
    }

    private fun setZoomBasedOnMarkers(locationMarkers: List<Location>) {
        val builder = LatLngBounds.Builder()

        locationMarkers.forEach {
            builder.include(it.toLatLong())
        }

        val bounds = builder.build()

        val width = resources.displayMetrics.widthPixels
        val height = (resources.displayMetrics.heightPixels / MAP_MARKERS_VERTICAL_PADDING_PERCENTAGE).toInt()
        val padding = (width * MAP_MARKERS_PADDING_PERCENTAGE).toInt()

        val cameraUpdateFactory = CameraUpdateFactory.newLatLngBounds(
            bounds,
            width,
            height,
            padding
        )

        map.animateCamera(cameraUpdateFactory)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap
        scaleViewModel.setItinerary(intent.getSerializableExtra(KEY_ITINERARY) as Itinerary)
        initViews()
    }
}

fun Location.toLatLong(): LatLng {
    return LatLng(
        latitude,
        longitude
    )
}