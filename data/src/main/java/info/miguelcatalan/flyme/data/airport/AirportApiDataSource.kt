package info.miguelcatalan.flyme.data.airport

import info.miguelcatalan.flyme.data.client.LufthansaApi
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.AirportDetailNotFoundError
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.repository.RxReadableDataSource
import io.reactivex.Observable
import java.util.concurrent.atomic.AtomicReference

class AirportApiDataSource(
    private val lufthansaApi: LufthansaApi,
    private val authRepository: RxBaseRepository<String, Auth>
) : RxReadableDataSource<String, Airport> {

    companion object {
        const val maxAmountOfItems: Int = 100
    }

    override fun getByKey(key: String): Observable<Airport> {
        return authRepository.getByKey(Auth.KEY)
            .flatMap { auth ->
                lufthansaApi.getAirport(
                    authorization = "Bearer ${auth.accessToken}",
                    airportCode = key
                ).map {
                    it.airportResource.airport.airport.toDomain()
                }
            }.onErrorResumeNext(Observable.error(AirportDetailNotFoundError()))
    }

    override fun getAll(): Observable<List<Airport>> {
        val atomicReference: AtomicReference<PaginatedData> = AtomicReference()
        atomicReference.set(PaginatedData())

        return authRepository.getByKey(Auth.KEY)
            .flatMap { auth ->
                getPaginatedAirports(auth = auth, atomicReference = atomicReference)
            }.repeatUntil {
                Thread.sleep(150)
                atomicReference.get().status == PaginatedStatus.COMPLETED
            }.flatMap {
                Observable.fromIterable(it)
            }.toList().toObservable()

    }

    private fun getPaginatedAirports(
        auth: Auth,
        atomicReference: AtomicReference<PaginatedData>
    ): Observable<List<Airport>> {
        var numberItemsToRetrieve = 0
        return lufthansaApi.getAirports(
            authorization = "Bearer ${auth.accessToken}",
            max = maxAmountOfItems,
            offset = atomicReference.get().currentOffset
        ).map { airportResponse ->
            numberItemsToRetrieve = airportResponse.airportsResource.meta.totalCount
            airportResponse.airportsResource.airports.airports.map {
                it.toDomain()
            }
        }.flatMap { airports ->
            calculatePagination(
                atomicReference = atomicReference,
                currentAirportsRetrieved = airports.size,
                totalAirportsToRetrieve = numberItemsToRetrieve
            )
            Observable.just(airports)
        }
    }

    private fun calculatePagination(
        atomicReference: AtomicReference<PaginatedData>,
        currentAirportsRetrieved: Int,
        totalAirportsToRetrieve: Int
    ) {
        val previousPagination = atomicReference.get()

        val nextOffset = previousPagination.currentOffset + maxAmountOfItems
        val retrieved = previousPagination.retrieved + currentAirportsRetrieved

        val status = if (retrieved >= totalAirportsToRetrieve) {
            PaginatedStatus.COMPLETED
        } else {
            PaginatedStatus.FETCHING
        }

        atomicReference.set(
            PaginatedData(
                currentOffset = nextOffset,
                retrieved = retrieved,
                status = status
            )
        )
    }
}

data class PaginatedData(
    val currentOffset: Int = 0,
    val retrieved: Int = 0,
    val status: PaginatedStatus = PaginatedStatus.FETCHING
)

enum class PaginatedStatus {
    FETCHING, COMPLETED
}