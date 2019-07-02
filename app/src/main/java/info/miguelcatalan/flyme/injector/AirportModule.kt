package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.airport.AirportApiDataSource
import info.miguelcatalan.flyme.data.repository.CachePolicyTtl
import info.miguelcatalan.flyme.data.repository.RxInMemoryCacheDataSource
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.AirportResource
import info.miguelcatalan.flyme.domain.airport.SearchForAirport
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.repository.TimeProvider
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val airportModule: Module = module {

    val REPO_VERSION: Int = 1

    factory(named("AirportApiDataSource")) {
        AirportApiDataSource(
            lufthansaApi = get(),
            authRepository = get(named("AuthRepository"))
        )
    }

    single(named("AirportRepository")) {
        RxBaseRepository<String, Airport>(
            rxReadableDataSources = listOf(
                get(named("AirportApiDataSource"))
            ),
            rxCacheableDataSources = listOf(
                get(named("AirportMemoryDataSource"))
            ),
            rxWriteableDataSources = emptyList()
        )
    }

    single(named("AirportMemoryDataSource")) {
        val timeProvider: TimeProvider = get(named("TimeProvider"))
        RxInMemoryCacheDataSource<String, Airport>(
            version = REPO_VERSION,
            timeProvider = timeProvider,
            policies = listOf(
                CachePolicyTtl.fiveMinutes(timeProvider = timeProvider)
            )
        )
    }

    single {
        AirportResource(get(named("AirportRepository")))
    }

    factory {
        SearchForAirport(
            airportResource = get(),
            threadScheduler = get(named("ThreadScheduler"))
        )
    }
}