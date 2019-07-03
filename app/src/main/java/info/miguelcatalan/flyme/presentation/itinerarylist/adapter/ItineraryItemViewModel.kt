package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import androidx.databinding.ObservableField
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

class ItineraryItemViewModel : BaseViewModel() {

    val arrival = ObservableField<Airport>()
    val departure = ObservableField<Airport>()

    val departureDate = ObservableField<String>()
    val departureTime = ObservableField<String>()
    val arrivalDate = ObservableField<String>()
    val arrivalTime = ObservableField<String>()

    fun setItinerary(itinerary: Itinerary) {
        val originStop = itinerary.scales.first().departure
        val destinationStop = itinerary.scales.last().arrival

        departure.set(originStop.airport)
        arrival.set(destinationStop.airport)

        departureDate.set(originStop.localDate.toReadableDate())
        departureTime.set(originStop.localDate.toReadableTime())
        arrivalDate.set(destinationStop.localDate.toReadableDate())
        arrivalTime.set(destinationStop.localDate.toReadableTime())
    }

}

fun Date.toReadableDate(): String {
    val formatter = SimpleDateFormat("MMM dd, EEE", Locale.getDefault())
    return formatter.format(this)
}

fun Date.toReadableTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(this)
}