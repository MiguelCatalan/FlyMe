package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.airport.AirportApiDataSource
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.AirportResource
import info.miguelcatalan.flyme.domain.airport.SearchForAirport
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val airportModule: Module = module {

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
            rxCacheableDataSources = emptyList(),
            rxWriteableDataSources = emptyList()
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