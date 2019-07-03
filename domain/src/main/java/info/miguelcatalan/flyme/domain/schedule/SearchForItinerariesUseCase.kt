package info.miguelcatalan.flyme.domain.schedule

import info.miguelcatalan.flyme.domain.executor.ThreadScheduler
import info.miguelcatalan.flyme.domain.executor.applyScheduler
import io.reactivex.Observable
import java.util.*

class SearchForItineraries(
    private val itineraryResource: ItineraryResource,
    private val threadScheduler: ThreadScheduler
) {
    operator fun invoke(
        departureAirportCode: String,
        arrivalAirportCode: String
    ): Observable<List<Itinerary>> =
        itineraryResource.getItineraries(
            ScheduleQuery(
                departureAirportCode = departureAirportCode,
                arrivalAirportCode = arrivalAirportCode,
                date = Date()//Today
            )
        ).applyScheduler(threadScheduler)
}