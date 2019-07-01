package info.miguelcatalan.flyme.domain.executor

import io.reactivex.Scheduler

interface ExecutionThread {
    fun getScheduler(): Scheduler
}