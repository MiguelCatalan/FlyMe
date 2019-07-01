package info.miguelcatalan.flyme.domain.executor

import io.reactivex.Observable

fun <T> Observable<T>.applyScheduler(scheduler: ThreadScheduler): Observable<T> {
    return compose(scheduler.apply<T>())
}