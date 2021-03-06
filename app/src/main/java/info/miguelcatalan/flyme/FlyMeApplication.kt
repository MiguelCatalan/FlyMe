package info.miguelcatalan.flyme

import android.app.Application
import info.miguelcatalan.flyme.domain.logger.Kog
import info.miguelcatalan.flyme.domain.logger.Logger
import info.miguelcatalan.flyme.injector.airportModule
import info.miguelcatalan.flyme.injector.appModule
import info.miguelcatalan.flyme.injector.authModule
import info.miguelcatalan.flyme.injector.dataModule
import info.miguelcatalan.flyme.injector.executorModule
import info.miguelcatalan.flyme.injector.itineraryModule
import info.miguelcatalan.flyme.logger.AndroidLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlyMeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initInjector()
    }

    private fun initInjector() {
        startKoin {
            androidLogger()
            androidContext(this@FlyMeApplication)
            androidFileProperties()
            modules(
                listOf(
                    appModule,
                    dataModule,
                    executorModule,
                    airportModule,
                    authModule,
                    itineraryModule
                )
            )
        }
    }

    private fun initLogger() {
        Kog.plant(AndroidLogger())
        Logger.i { "Logger planted" }
    }
}