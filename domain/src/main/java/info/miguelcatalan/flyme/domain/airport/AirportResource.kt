package info.miguelcatalan.flyme.domain.airport

import info.miguelcatalan.flyme.domain.logger.Logger
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import io.reactivex.Observable

class AirportResource(
    private val repository: RxBaseRepository<String, Airport>
) {

    fun searchForAirport(searchTerm: String): Observable<List<Airport>> {
        return repository.getAll()
            .flatMap {
                Observable.fromIterable(it)
            }
            .filter {
                (it.name.contains(
                    searchTerm,
                    ignoreCase = true
                ) || it.airportCode.contains(
                    searchTerm,
                    ignoreCase = true
                ))
            }
            .toList()
            .toObservable()
    }
}