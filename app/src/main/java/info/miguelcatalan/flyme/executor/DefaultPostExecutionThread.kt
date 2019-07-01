package info.miguelcatalan.flyme.executor

import info.miguelcatalan.flyme.domain.executor.ExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class DefaultPostExecutionThread : ExecutionThread {

    override fun getScheduler(): Scheduler =
        AndroidSchedulers.mainThread()
}