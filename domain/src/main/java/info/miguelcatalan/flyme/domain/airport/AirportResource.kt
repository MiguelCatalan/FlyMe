package info.miguelcatalan.flyme.domain.airport

import io.reactivex.Observable

class AirportResource(
    private val airportApi: AirportApi
) {

    fun searchForAirport(searchTerm: String): Observable<List<Airport>> {
        return airportApi.getAllAirports().map { airports ->
            val filteredAirports: MutableList<Airport> = mutableListOf()
            filteredAirports.addAll(airports.filter { airport -> airport.name.contains(searchTerm) })
            filteredAirports.addAll(airports.filter { airport -> airport.airportCode.contains(searchTerm) })
            filteredAirports
        }
    }
}