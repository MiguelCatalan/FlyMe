package info.miguelcatalan.flyme.domain.utils

import io.reactivex.observers.TestObserver

fun <T> TestObserver<T>.value(): T {
    return this.values().first()
}