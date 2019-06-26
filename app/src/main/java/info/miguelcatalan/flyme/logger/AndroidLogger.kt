package info.miguelcatalan.flyme.logger

import android.util.Log
import info.miguelcatalan.flyme.domain.logger.LogLevel
import info.miguelcatalan.flyme.domain.logger.LoggerTree

class AndroidLogger : LoggerTree {
    override fun log(tag: () -> String, level: LogLevel, msg: () -> String, throwable: Throwable?) {
        when (level) {
            LogLevel.ERROR -> Log.e(tag(), msg(), throwable)
            LogLevel.WARN -> Log.w(tag(), msg())
            LogLevel.INFO -> Log.i(tag(), msg())
            LogLevel.DEBUG -> Log.d(tag(), msg())
            LogLevel.VERBOSE -> Log.v(tag(), msg())
        }
    }
}