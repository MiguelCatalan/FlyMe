package info.miguelcatalan.flyme.domain.executor

import io.reactivex.ObservableTransformer

interface ThreadScheduler {
    fun <T> apply(): ObservableTransformer<T, T>
}