package info.miguelcatalan.flyme.domain.schedule

import info.miguelcatalan.flyme.domain.airport.Airport
import java.io.Serializable
import java.util.Date

data class Itinerary(
    val departure: Airport,
    val arrival: Airport,
    val date: Date,
    val scales: List<Scale>
) : Serializable

data class Scale(
    val departure: StopInfo,
    val arrival: StopInfo,
    val carrier: Carrier
) : Serializable

data class StopInfo(
    val airport: Airport,
    val localDate: Date,
    val terminal: String? = null
) : Serializable