package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.repository.CachePolicyTtl
import info.miguelcatalan.flyme.data.repository.RxInMemoryCacheDataSource
import info.miguelcatalan.flyme.data.schedules.ScheduleApiDataSource
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.repository.TimeProvider
import info.miguelcatalan.flyme.domain.schedule.ItineraryResource
import info.miguelcatalan.flyme.domain.schedule.ScheduleOptions
import info.miguelcatalan.flyme.domain.schedule.ScheduleQuery
import info.miguelcatalan.flyme.domain.schedule.SearchForItineraries
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val itineraryModule: Module = module {

    val REPO_VERSION = 1

    factory(named("ScheduleApiDataSource")) {
        ScheduleApiDataSource(
            lufthansaApi = get(),
            authRepository = get(named("AuthRepository"))
        )
    }

    single(named("ScheduleRepository")) {
        RxBaseRepository<ScheduleQuery, ScheduleOptions>(
            rxReadableDataSources = listOf(
                get(named("ScheduleApiDataSource"))
            ),
            rxCacheableDataSources = listOf(
                get(named("ScheduleMemoryDataSource"))
            ),
            rxWriteableDataSources = emptyList()
        )
    }

    single(named("ScheduleMemoryDataSource")) {
        val timeProvider: TimeProvider = get(named("TimeProvider"))
        RxInMemoryCacheDataSource<ScheduleQuery, ScheduleOptions>(
            version = REPO_VERSION,
            timeProvider = timeProvider,
            policies = listOf(
                CachePolicyTtl.oneMinute(timeProvider = timeProvider)
            )
        )
    }

    single {
        ItineraryResource(
            airportRepository = get(named("AirportRepository")),
            schedulesRepository = get(named("ScheduleRepository"))
        )
    }

    factory {
        SearchForItineraries(
            itineraryResource = get(),
            threadScheduler = get(named("ThreadScheduler"))
        )
    }
}