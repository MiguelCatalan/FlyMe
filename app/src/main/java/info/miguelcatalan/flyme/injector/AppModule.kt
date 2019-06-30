package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    viewModel { SearchViewModel() }
}