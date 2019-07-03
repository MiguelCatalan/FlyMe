package info.miguelcatalan.flyme.presentation.itinerarylist

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.logger.Logger
import info.miguelcatalan.flyme.domain.notifier.Notifier
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import info.miguelcatalan.flyme.domain.schedule.SearchForItineraries
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy

class ItineraryListViewModel(
    private val searchForItineraries: SearchForItineraries,
    private val notifier: Notifier
) : BaseViewModel() {

    var origin = MutableLiveData<Airport?>()
    var destination = MutableLiveData<Airport?>()
    var itineraries = MutableLiveData<List<Itinerary>>()
    var isLoading = MutableLiveData<Boolean>()

    fun setOrigin(airport: Airport) {
        origin.value = airport
        tryToRequestItineraries()
    }

    fun setDestination(airport: Airport) {
        destination.value = airport
        tryToRequestItineraries()
    }

    private fun tryToRequestItineraries() {
        if (origin.value != null && destination.value != null) {
            requestItineraries()
        }
    }

    private fun requestItineraries() {
        searchForItineraries(
            departureAirportCode = origin.value!!.key,
            arrivalAirportCode = destination.value!!.key
        ).doOnSubscribe {
            isLoading.value = true
        }.subscribeBy(
            onNext = {
                isLoading.value = false
                itineraries.value = it
            }, onError = {
                Logger.e(throwable = it, msg = {
                    it.message!!
                })
                notifier.show(message = it.message!!)
                itineraries.value = emptyList()
            }
        ).addDisposableTo(disposableBag)
    }
}