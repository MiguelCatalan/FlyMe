package info.miguelcatalan.flyme.presentation.scale

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ScaleLayoutBinding
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import kotlinx.android.synthetic.main.scale_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ScaleActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val KEY_ITINERARY = "key_itinerary"
    }

    private lateinit var map: GoogleMap

    private val scaleViewModel: ScaleViewModel by viewModel { parametersOf(this@ScaleActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        scaleViewModel.setItinerary(intent.getSerializableExtra(KEY_ITINERARY) as Itinerary)
        initViews()

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
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.map = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        this.map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        this.map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
