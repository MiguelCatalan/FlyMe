package info.miguelcatalan.flyme.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val disposableBag = CompositeDisposable()

    override fun onCleared() {
        disposableBag.clear()
    }

    fun Disposable.addDisposableTo(bag: CompositeDisposable): Disposable = apply { bag.add(this) }
}