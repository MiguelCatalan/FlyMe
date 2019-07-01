package info.miguelcatalan.flyme.presentation.search

import androidx.lifecycle.MutableLiveData
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.SearchForAirport
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SearchViewModel(
    private val searchForAirport: SearchForAirport
) : BaseViewModel() {

    companion object {
        const val DELAY_BETWEEN_SEARCHES_IN_MILLIS = 500L
        const val MIN_CHARS_FOR_SEARCH = 3
    }

    private var searchTerm = MutableLiveData<String>()
    private var airports = MutableLiveData<List<Airport>>()
    private var isLoading = MutableLiveData<Boolean>()

    private val subject = PublishSubject.create<String>()

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
            .switchMap {
                isLoading.value = true
                searchForAirport(it)
            }
            .subscribeBy(onNext = {
                isLoading.value = false
                airports.value = it
            }).addDisposableTo(disposableBag)

    }

    private fun resetSearch() {
        searchTerm.value = ""
    }

    fun search(term: String) {
        subject.onNext(term)
    }

    fun getAirports() = airports

    fun getSearchTerm() = searchTerm

    fun getIsLoading() = isLoading
}