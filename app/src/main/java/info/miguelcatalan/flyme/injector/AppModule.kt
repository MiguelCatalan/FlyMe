package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.domain.repository.ApplicationTimeProvider
import info.miguelcatalan.flyme.notifier.ToastNotifier
import info.miguelcatalan.flyme.presentation.itinerarylist.ItineraryListActivity
import info.miguelcatalan.flyme.presentation.itinerarylist.ItineraryListViewModel
import info.miguelcatalan.flyme.presentation.navigator.AppNavigator
import info.miguelcatalan.flyme.presentation.scale.ScaleViewModel
import info.miguelcatalan.flyme.presentation.search.SearchActivity
import info.miguelcatalan.flyme.presentation.search.SearchViewModel
import info.miguelcatalan.flyme.presentation.tripselector.TripSelectorActivity
import info.miguelcatalan.flyme.presentation.tripselector.TripSelectorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule: Module = module {

    factory(named("TimeProvider")) {
        ApplicationTimeProvider()
    }

    viewModel { (activity: SearchActivity) ->
        SearchViewModel(
            get(),
            ToastNotifier(activity)
        )
    }
    viewModel { (activity: TripSelectorActivity) ->
        TripSelectorViewModel(
            AppNavigator(activity)
        )
    }
    viewModel { (activity: ItineraryListActivity) ->
        ItineraryListViewModel(
            get(),
            AppNavigator(activity),
            ToastNotifier(activity)
        )
    }
    viewModel {
        ScaleViewModel()
    }
}