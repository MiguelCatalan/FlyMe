package info.miguelcatalan.flyme.data.schedules

import com.google.gson.annotations.SerializedName
import info.miguelcatalan.flyme.domain.schedule.AirportTime
import info.miguelcatalan.flyme.domain.schedule.Carrier
import info.miguelcatalan.flyme.domain.schedule.Flight
import info.miguelcatalan.flyme.domain.schedule.Schedule
import java.util.*

data class SchedulesResponse(
    @SerializedName("ScheduleResource")
    val scheduleResource: ScheduleResource
)

data class ScheduleResource(
    @SerializedName("Schedule")
    val schedules: List<ScheduleApi>
)

data class ScheduleApi(
    @SerializedName("Flight")
    val flights: List<FlightApi>
) {

    fun toDomain(): Schedule =
        Schedule(
            departureAirportCode = flights.first().departure.airportCode,
            arrivalAirportCode = flights.last().arrival.airportCode,
            date = Date(),
            flights = flights.map { it.toDomain() }
        )
}

data class FlightApi(
    @SerializedName("Departure")
    val departure: AirportTimeApi,

    @SerializedName("Arrival")
    val arrival: AirportTimeApi,

    @SerializedName("MarketingCarrier")
    val marketingCarrier: MarketingCarrierApi
) {

    fun toDomain(): Flight = Flight(
        departure = departure.toDomain(),
        arrival = arrival.toDomain(),
        carrier = marketingCarrier.toDomain()
    )
}

data class AirportTimeApi(
    @SerializedName("AirportCode")
    val airportCode: String,

    @SerializedName("ScheduledTimeLocal")
    val scheduledTimeLocal: ScheduledTimeLocalApi,

    @SerializedName("Terminal")
    val terminal: TerminalApi?
) {

    fun toDomain(): AirportTime = AirportTime(
        airportCode = airportCode,
        dateTime = scheduledTimeLocal.dateTime,
        terminal = terminal?.name
    )
}

data class ScheduledTimeLocalApi(
    @SerializedName("DateTime")
    val dateTime: String
)

data class TerminalApi(
    @SerializedName("Name")
    val name: String
)

data class MarketingCarrierApi(
    @SerializedName("AirlineID")
    val airlineID: String,

    @SerializedName("FlightNumber")
    val flightNumber: Int
) {

    fun toDomain(): Carrier = Carrier(
        airlineID = airlineID,
        flightNumber = flightNumber
    )
}