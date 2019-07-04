package info.miguelcatalan.flyme.domain.airport

import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import io.reactivex.Observable

class AirportResource(
    private val repository: RxBaseRepository<String, Airport>
) {

    fun searchForAirport(searchTerm: String): Observable<List<Airport>> {
        return repository.getAll()
            .flatMap {airports ->
                Observable.fromIterable(airports)
            }
            .filter {airport ->
                (airport.name.contains(
                    searchTerm,
                    ignoreCase = true
                ) || airport.airportCode.contains(
                    searchTerm,
                    ignoreCase = true
                ))
            }
            .toList()
            .toObservable()
    }
}