package info.miguelcatalan.flyme.data.repository

import info.miguelcatalan.flyme.domain.repository.ApplicationTimeProvider
import info.miguelcatalan.flyme.domain.repository.CacheItemPolicy
import info.miguelcatalan.flyme.domain.repository.CachePolicy

class RxInMemoryCacheDataSourceMother {

    companion object {
        const val CACHE_DATA_SOURCE_VERSION = 1
    }

    lateinit var cachePolicy: CachePolicy

    fun setupCachePolicyToReturnValidData() {
        cachePolicy = object : CachePolicy {
            override fun isValid(cacheItem: CacheItemPolicy): Boolean = true
        }
    }

    fun setupCachePolicyToReturnInvalidData() {
        cachePolicy = object : CachePolicy {
            override fun isValid(cacheItem: CacheItemPolicy): Boolean = false
        }
    }

    fun getRxInMemoryCacheDataSource(): RxInMemoryCacheDataSource<AnyRepositoryKey, AnyRepositoryValue> =
        RxInMemoryCacheDataSource(CACHE_DATA_SOURCE_VERSION, ApplicationTimeProvider(), listOf(cachePolicy))
}