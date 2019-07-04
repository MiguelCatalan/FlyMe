package info.miguelcatalan.flyme.injector

import info.miguelcatalan.flyme.executor.DefaultPostExecutionThread
import info.miguelcatalan.flyme.executor.DefaultPreExecutionThread
import info.miguelcatalan.flyme.executor.DefaultThreadScheduler
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val executorModule: Module = module {

    single(named("ThreadScheduler")) {
        DefaultThreadScheduler(
            preExecutionThread = DefaultPreExecutionThread(),
            postExecutionThread = DefaultPostExecutionThread()
        )
    }
}