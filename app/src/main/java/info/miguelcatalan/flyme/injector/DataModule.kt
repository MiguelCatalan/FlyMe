package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.data.client.ApiClientBuilder
import info.miguelcatalan.flyme.data.client.LufthansaApi
import org.koin.core.module.Module
import org.koin.dsl.module

var dataModule: Module = module {
    factory { ApiClientBuilder(getProperty("lufthansa_api_url")).create(LufthansaApi::class.java) }
}