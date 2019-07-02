package info.miguelcatalan.flyme.domain.schedule

import info.miguelcatalan.flyme.domain.repository.Identifiable
import java.util.*

data class ScheduleOptions(
    val options: List<Schedule>
) : Identifiable<ScheduleQuery> {

    override val key: ScheduleQuery
        get() = ScheduleQuery(
            departureAirportCode = options.first().departureAirportCode,
            arrivalAirportCode = options.first().arrivalAirportCode,
            date = options.first().date
        )

}

data class ScheduleQuery(
    val departureAirportCode: String,
    val arrivalAirportCode: String,
    val date: Date
)