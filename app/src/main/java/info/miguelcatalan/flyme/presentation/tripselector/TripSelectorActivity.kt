package info.miguelcatalan.flyme.presentation.tripselector

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.databinding.TripselectorLayoutBinding
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.presentation.customview.TripStopConfiguration
import info.miguelcatalan.flyme.presentation.customview.TripStopType
import info.miguelcatalan.flyme.presentation.search.SearchActivity
import info.miguelcatalan.flyme.presentation.search.SelectionType
import info.miguelcatalan.flyme.presentation.search.SelectionType.DESTINATION
import info.miguelcatalan.flyme.presentation.search.SelectionType.ORIGIN
import kotlinx.android.synthetic.main.tripselector_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TripSelectorActivity : AppCompatActivity() {
    private val searchViewModel: TripSelectorViewModel by viewModel { parametersOf(this@TripSelectorActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        configureViews()
    }

    private fun initBinding() {
        val activitySearchBinding: TripselectorLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.tripselector_layout)

        activitySearchBinding.apply {
            lifecycleOwner = this@TripSelectorActivity
            viewModel = searchViewModel
        }
    }

    private fun configureViews() {
        originStop.configuration = TripStopConfiguration(
            stopType = TripStopType.Origin
        )
        originStop.onPressed {
            searchViewModel.onOriginPressed()
        }
        searchViewModel.getOrigin().observe(this, Observer {airport ->
            originStop.stop = airport
        })

        destinationStop.configuration = TripStopConfiguration(
            stopType = TripStopType.Destination
        )
        destinationStop.onPressed {
            searchViewModel.onDestinationPressed()
        }
        searchViewModel.getDestination().observe(this, Observer {airport ->
            destinationStop.stop = airport
        })

        searchFlights.text = R.string.selector_search
        searchViewModel.getCanSearchFlights().observe(this, Observer { canSearch ->
            searchFlights.enable(canSearch)
        })
        searchFlights.setOnClickListener {
            searchViewModel.onSearchFlightsPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SearchActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                data?.apply {
                    val type = getSerializableExtra(SearchActivity.KEY_TYPE) as SelectionType
                    val airport = getSerializableExtra(SearchActivity.KEY_AIRPORT) as Airport

                    when (type) {
                        ORIGIN -> searchViewModel.setOrigin(airport)
                        DESTINATION -> searchViewModel.setDestination(airport)
                    }
                }
            }
        }
    }
}