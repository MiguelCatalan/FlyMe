package info.miguelcatalan.flyme.executor

import info.miguelcatalan.flyme.domain.executor.ExecutionThread
import info.miguelcatalan.flyme.domain.executor.ThreadScheduler
import io.reactivex.ObservableTransformer

class DefaultThreadScheduler(
    private val preExecutionThread: ExecutionThread,
    private val postExecutionThread: ExecutionThread
) : ThreadScheduler {

    override fun <T> apply(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(preExecutionThread.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
        }
    }
}