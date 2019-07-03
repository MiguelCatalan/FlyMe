package info.miguelcatalan.flyme.domain.schedule

import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*

class ItineraryResource(
    private val airportRepository: RxBaseRepository<String, Airport>,
    private val schedulesRepository: RxBaseRepository<ScheduleQuery, ScheduleOptions>
) {

    fun getItineraries(query: ScheduleQuery): Observable<List<Itinerary>> {
        return schedulesRepository.getByKey(query)
            .map {
                it.options.map { scheduleOption ->
                    populateItinerariesWithAirportsFromCodes(scheduleOption)
                }
            }
    }

    fun getAirportByCode(airportCode: String): Airport? {
        var airport: Airport? = null
        val disposable = airportRepository.getByKey(airportCode).subscribeBy(
            onNext = {
                airport = it
            }
        )
        disposable.dispose()
        return airport
    }

    private fun populateItinerariesWithAirportsFromCodes(scheduleOption: Schedule): Itinerary {
        return Itinerary(
            departure = getAirportByCode(scheduleOption.departureAirportCode)!!,
            arrival = getAirportByCode(scheduleOption.arrivalAirportCode)!!,
            date = scheduleOption.date,
            scales = scheduleOption.flights.map { flight ->
                Scale(
                    carrier = flight.carrier,
                    departure = flight.departure.toStopInfo(),
                    arrival = flight.arrival.toStopInfo()
                )
            }
        )
    }

    private fun AirportInfo.toStopInfo(): StopInfo {
        return StopInfo(
            airport = getAirportByCode(airportCode)!!,
            localDate = Date(), //TODO see this
            terminal = terminal
        )
    }
}