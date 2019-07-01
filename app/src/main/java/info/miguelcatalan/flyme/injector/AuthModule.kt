package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.auth.AuthApiDataSource
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val authModule: Module = module {
    factory(named("AuthApiDataSource")) {
        AuthApiDataSource(
            lufthansaApi = get(),
            lufthansaApiKey = getProperty("lufthansa_api_key"),
            lufthansaApiSecret = getProperty("lufthansa_api_secret")
        )
    }

    single(named("AuthRepository")) {
        RxBaseRepository<String, Auth>(
            rxReadableDataSources = listOf(
                get(named("AuthApiDataSource"))
            ),
            rxWriteableDataSources = emptyList(),
            rxCacheableDataSources = emptyList()
        )
    }
}