package info.miguelcatalan.flyme.presentation.tripselector

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import info.miguelcatalan.flyme.presentation.navigator.AppNavigator
import info.miguelcatalan.flyme.presentation.search.SelectionType

class TripSelectorViewModel(
    private val navigator: AppNavigator
) : BaseViewModel() {

    private var origin = MutableLiveData<Airport?>()
    private var destination = MutableLiveData<Airport?>()
    private var canSearchFlights = MutableLiveData<Boolean>()

    init {
        canSearchFlights.value = false
    }

    fun onOriginPressed() {
        navigator.navigateToSearchAirport(SelectionType.ORIGIN)
    }

    fun onDestinationPressed() {
        navigator.navigateToSearchAirport(SelectionType.DESTINATION)
    }

    fun onSearchFlightsPressed() {

    }

    fun setOrigin(airport: Airport) {
        origin.value = airport
        checkStops()
    }

    fun setDestination(airport: Airport) {
        destination.value = airport
        checkStops()
    }

    private fun checkStops() {
        canSearchFlights.value = origin.value != null && destination.value != null
    }

    fun getOrigin() = origin
    fun getDestination() = destination
    fun getCanSearchFlights() = canSearchFlights
}