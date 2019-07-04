package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.R
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.schedule.Itinerary
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import info.miguelcatalan.flyme.presentation.base.ResourceResolver
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ItineraryItemViewModel(
    private val resourceResolver: ResourceResolver
) : BaseViewModel() {

    val arrival = MutableLiveData<Airport>()
    val departure = MutableLiveData<Airport>()

    val departureDate = MutableLiveData<String>()
    val departureTime = MutableLiveData<String>()
    val arrivalDate = MutableLiveData<String>()
    val arrivalTime = MutableLiveData<String>()
    val scaleNumber = MutableLiveData<String>()

    fun setItinerary(itinerary: Itinerary) {
        val originStop = itinerary.scales.first().departure
        val destinationStop = itinerary.scales.last().arrival

        departure.value = originStop.airport
        arrival.value = destinationStop.airport

        departureDate.value = originStop.localDate.toReadableDate()
        departureTime.value = originStop.localDate.toReadableTime()
        arrivalDate.value = destinationStop.localDate.toReadableDate()
        arrivalTime.value = destinationStop.localDate.toReadableTime()
        scaleNumber.value = getScaleText(itinerary.scales.size - 1)
    }

    private fun getScaleText(numberOfStops: Int): String {
        return when (numberOfStops) {
            0 -> resourceResolver.getStringResource(R.string.itinerary_scales_direct)
            else -> resourceResolver.getQuantityStringResource(
                R.plurals.itinerary_scales_with_stops,
                numberOfStops,
                numberOfStops.toString()
            )
        }
    }

    private fun Date.toReadableDate(): String {
        val formatter = SimpleDateFormat("MMM dd, EEE", Locale.getDefault())
        return formatter.format(this)
    }

    private fun Date.toReadableTime(): String {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(this)
    }

}