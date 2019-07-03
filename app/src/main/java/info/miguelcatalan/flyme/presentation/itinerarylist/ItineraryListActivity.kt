package info.miguelcatalan.flyme.presentation.itinerarylist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.ItineraryListLayoutBinding
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.presentation.customview.TripStopConfiguration
import info.miguelcatalan.flyme.presentation.customview.TripStopType
import info.miguelcatalan.flyme.presentation.itinerarylist.adapter.ItineraryAdapter
import kotlinx.android.synthetic.main.itinerary_list_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ItineraryListActivity : AppCompatActivity() {

    companion object {
        const val KEY_ORIGIN = "key_origin"
        const val KEY_DESTINATION = "key_destination"
    }

    private val itineraryListViewModel: ItineraryListViewModel by viewModel { parametersOf(this@ItineraryListActivity) }
    private lateinit var groupLayoutManager: LinearLayoutManager
    private val itineraryAdapter = ItineraryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        itineraryListViewModel.setOrigin(intent.getSerializableExtra(KEY_ORIGIN) as Airport)
        itineraryListViewModel.setDestination(intent.getSerializableExtra(KEY_DESTINATION) as Airport)
        configureList()
        configureView()
    }

    private fun configureView() {
        originStop.configuration = TripStopConfiguration(
            stopType = TripStopType.Origin,
            selectable = false
        )
        itineraryListViewModel.origin.observe(this, Observer {
            originStop.stop = it
        })

        destinationStop.configuration = TripStopConfiguration(
            stopType = TripStopType.Destination,
            selectable = false
        )
        itineraryListViewModel.destination.observe(this, Observer {
            destinationStop.stop = it
        })
    }

    private fun initBinding() {
        val scheduleListLayoutBinding: ItineraryListLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.itinerary_list_layout)

        scheduleListLayoutBinding.apply {
            lifecycleOwner = this@ItineraryListActivity
            viewModel = itineraryListViewModel
        }
    }

    private fun configureList() {
        groupLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        itineraryList.apply {
            adapter = itineraryAdapter
            layoutManager = groupLayoutManager
            setHasFixedSize(true)
        }

        itineraryListViewModel.itineraries.observe(this, Observer {
            itineraryAdapter.updateItineraries(it)
        })

        itineraryListViewModel.isLoading.observe(this, Observer {
            itineraryAdapter.isLoading = it
        })
    }
}
