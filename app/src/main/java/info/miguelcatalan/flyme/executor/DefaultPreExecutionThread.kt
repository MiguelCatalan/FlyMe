package info.miguelcatalan.flyme.executor

import info.miguelcatalan.flyme.domain.executor.ExecutionThread
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class DefaultPreExecutionThread : ExecutionThread {

    override fun getScheduler(): Scheduler =
        Schedulers.io()
}