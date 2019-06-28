package info.miguelcatalan.flyme.domain.repository

data class AnyRepositoryKey(val key: Int)
data class AnyRepositoryValue(override val key: AnyRepositoryKey) : Identifiable<AnyRepositoryKey>