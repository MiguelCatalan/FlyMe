package info.miguelcatalan.flyme.domain.airport

import io.reactivex.Observable

interface AirportApi {
    fun getAllAirports(): Observable<List<Airport>>
}