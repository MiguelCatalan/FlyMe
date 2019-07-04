package info.miguelcatalan.flyme.presentation.search

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.SearchForAirport
import info.miguelcatalan.flyme.domain.notifier.Notifier
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(
    private val searchForAirport: SearchForAirport,
    private val notifier: Notifier
) : BaseViewModel() {

    companion object {
        const val DELAY_BETWEEN_SEARCHES_IN_MILLIS = 500L
        const val MIN_CHARS_FOR_SEARCH = 3
    }

    val searchTerm = MutableLiveData<String>()
    val airports = MutableLiveData<List<Airport>>()
    val isLoading = MutableLiveData<Boolean>()

    private val subject = PublishSubject.create<String>()

    lateinit var stopType: SelectionType

    init {
        initSearch()
    }

    private fun initSearch() {
        resetSearch()
        subject
            .debounce(DELAY_BETWEEN_SEARCHES_IN_MILLIS, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .filter { text -> text.length >= MIN_CHARS_FOR_SEARCH }
            .observeOn(AndroidSchedulers.mainThread())
            .switchMap { term ->
                isLoading.value = true
                searchForAirport(term)
            }
            .subscribeBy(
                onNext = { airportList ->
                    isLoading.value = false
                    airports.value = airportList
                }, onError = { throwable ->
                    notifier.show(message = throwable.message!!)
                    airports.value = emptyList()
                }
            ).addDisposableTo(disposableBag)
    }

    private fun resetSearch() {
        searchTerm.value = ""
    }

    fun search(term: String) {
        subject.onNext(term)
    }
}