package info.miguelcatalan.flyme.domain.schedule

import java.io.Serializable
import java.util.Date

data class Schedule(
    val departureAirportCode: String,
    val arrivalAirportCode: String,
    val date: Date,
    val flights: List<Flight>
)

data class Flight(
    val departure: AirportInfo,
    val arrival: AirportInfo,
    val carrier: Carrier
)

data class AirportInfo(
    val airportCode: String,
    val dateTime: Date,
    val terminal: String? = null
)

data class Carrier(
    val airlineID: String,
    val flightNumber: Int
) : Serializable