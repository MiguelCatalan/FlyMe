package info.miguelcatalan.flyme

import android.app.Application
import info.miguelcatalan.flyme.domain.logger.Kog
import info.miguelcatalan.flyme.domain.logger.Logger
import info.miguelcatalan.flyme.logger.AndroidLogger


class FlyMeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Kog.plant(AndroidLogger())
        Logger.i { "Logger planted" }
    }
}