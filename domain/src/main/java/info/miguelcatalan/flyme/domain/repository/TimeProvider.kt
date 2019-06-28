package info.miguelcatalan.flyme.domain.repository

import java.util.*

interface TimeProvider {
    fun currentTimeMillis(): Long
    fun currentDate(): Date
}

class ApplicationTimeProvider : TimeProvider {

    override fun currentTimeMillis(): Long =
        System.currentTimeMillis()

    override fun currentDate(): Date = Date()
}
