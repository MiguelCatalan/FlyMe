package info.miguelcatalan.flyme.presentation.search

import info.miguelcatalan.flyme.domain.airport.SearchForAirport
import info.miguelcatalan.flyme.domain.logger.Logger
import info.miguelcatalan.flyme.presentation.base.BaseViewModel
import io.reactivex.rxkotlin.subscribeBy

class SearchViewModel(
    searchForAirport: SearchForAirport
) : BaseViewModel() {

    init {
        searchForAirport("MAD")
            .subscribeBy(
                onNext = {
                    it.forEach {
                        Logger.d {
                            "Airport: ${it.airportCode}"
                        }
                    }
                },
                onError = {
                    Logger.d {
                        "Airport error: ${it.message}"
                    }
                },
                onComplete = {
                    Logger.d {
                        "Airport completed"
                    }
                }
            ).addDisposableTo(disposableBag)
    }
}