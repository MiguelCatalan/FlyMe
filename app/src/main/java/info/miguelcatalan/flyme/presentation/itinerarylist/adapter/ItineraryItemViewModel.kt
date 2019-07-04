package info.miguelcatalan.flyme.presentation.itinerarylist.adapter

import androidx.databinding.ObservableField
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

    val arrival = ObservableField<Airport>()
    val departure = ObservableField<Airport>()

    val departureDate = ObservableField<String>()
    val departureTime = ObservableField<String>()
    val arrivalDate = ObservableField<String>()
    val arrivalTime = ObservableField<String>()
    val scaleNumber = ObservableField<String>()

    fun setItinerary(itinerary: Itinerary) {
        val originStop = itinerary.scales.first().departure
        val destinationStop = itinerary.scales.last().arrival

        departure.set(originStop.airport)
        arrival.set(destinationStop.airport)

        departureDate.set(originStop.localDate.toReadableDate())
        departureTime.set(originStop.localDate.toReadableTime())
        arrivalDate.set(destinationStop.localDate.toReadableDate())
        arrivalTime.set(destinationStop.localDate.toReadableTime())
        scaleNumber.set(getScaleText(itinerary.scales.size - 1))
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

}

fun Date.toReadableDate(): String {
    val formatter = SimpleDateFormat("MMM dd, EEE", Locale.getDefault())
    return formatter.format(this)
}

fun Date.toReadableTime(): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(this)
}