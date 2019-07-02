package info.miguelcatalan.flyme.data.repository

import info.miguelcatalan.flyme.domain.repository.Identifiable


data class AnyRepositoryKey(val key: Int)

data class AnyRepositoryValue(override val key: AnyRepositoryKey) : Identifiable<AnyRepositoryKey>