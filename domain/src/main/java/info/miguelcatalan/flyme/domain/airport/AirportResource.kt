package info.miguelcatalan.flyme.domain.airport

import io.reactivex.Observable

class AirportResource(
    private val airportApi: AirportApi
) {

    fun searchForAirport(searchTerm: String): Observable<List<Airport>> {
        return airportApi.getAllAirports()
            .flatMap { Observable.fromIterable(it) }
            .filter {
                (it.name.contains(searchTerm) || it.airportCode.contains(searchTerm))
            }
            .toList()
            .toObservable()
    }
}