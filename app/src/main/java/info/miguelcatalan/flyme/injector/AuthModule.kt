package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.auth.AuthApiDataSource
import info.miguelcatalan.flyme.data.repository.CachePolicyTtl
import info.miguelcatalan.flyme.data.repository.RxInMemoryCacheDataSource
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.repository.TimeProvider
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule: Module = module {

    val REPO_VERSION = 1

    factory(named("AuthApiDataSource")) {
        AuthApiDataSource(
            lufthansaApi = get(),
            lufthansaApiKey = getProperty("lufthansa_api_key"),
            lufthansaApiSecret = getProperty("lufthansa_api_secret")
        )
    }

    single(named("AuthMemoryDataSource")) {
        val timeProvider: TimeProvider = get(named("TimeProvider"))
        RxInMemoryCacheDataSource<String, Auth>(
            version = REPO_VERSION,
            timeProvider = timeProvider,
            policies = listOf(
                CachePolicyTtl.fiveMinutes(timeProvider = timeProvider)
            )
        )
    }

    single(named("AuthRepository")) {
        RxBaseRepository<String, Auth>(
            rxReadableDataSources = listOf(
                get(named("AuthApiDataSource"))
            ),
            rxWriteableDataSources = emptyList(),
            rxCacheableDataSources = listOf(
                get(named("AuthMemoryDataSource"))
            )
        )
    }
}