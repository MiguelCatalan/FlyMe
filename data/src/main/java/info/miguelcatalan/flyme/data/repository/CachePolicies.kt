package info.miguelcatalan.flyme.data.repository

import info.miguelcatalan.flyme.domain.repository.CacheItemPolicy
import info.miguelcatalan.flyme.domain.repository.CachePolicy
import info.miguelcatalan.flyme.domain.repository.TimeProvider
import java.util.concurrent.TimeUnit

class CachePolicyTtl(
    ttl: Int,
    timeUnit: TimeUnit,
    private val timeProvider: TimeProvider
) : CachePolicy {

    private val ttlMillis: Long = timeUnit.toMillis(ttl.toLong())

    override fun isValid(cacheItem: CacheItemPolicy): Boolean {
        val lifeTime = cacheItem.timestamp + ttlMillis
        return lifeTime > timeProvider.currentTimeMillis()
    }

    companion object {

        fun fiveMinutes(timeProvider: TimeProvider): CachePolicyTtl {
            return CachePolicyTtl(ttl = 5, timeUnit = TimeUnit.MINUTES, timeProvider = timeProvider)
        }
    }
}