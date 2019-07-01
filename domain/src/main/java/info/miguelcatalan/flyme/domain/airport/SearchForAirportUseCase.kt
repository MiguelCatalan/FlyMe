package info.miguelcatalan.flyme.domain.airport

import info.miguelcatalan.flyme.domain.executor.ThreadScheduler
import info.miguelcatalan.flyme.domain.executor.applyScheduler
import io.reactivex.Observable

class SearchForAirport(
    private val airportResource: AirportResource,
    private val threadScheduler: ThreadScheduler
) {
    operator fun invoke(term: String): Observable<List<Airport>> =
        airportResource.searchForAirport(searchTerm = term)
            .applyScheduler(threadScheduler)
}