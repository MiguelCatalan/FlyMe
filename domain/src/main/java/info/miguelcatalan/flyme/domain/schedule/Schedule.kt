package info.miguelcatalan.flyme.domain.schedule

import java.util.*

data class Schedule(
    val departureAirportCode: String,
    val arrivalAirportCode: String,
    val date: Date,
    val flights: List<Flight>
)

data class Flight(
    val departure: AirportTime,
    val arrival: AirportTime,
    val carrier: Carrier
)

data class AirportTime(
    val airportCode: String,
    val dateTime: String,
    val terminal: String? = null
)

data class Carrier(
    val airlineID: String,
    val flightNumber: Int
)