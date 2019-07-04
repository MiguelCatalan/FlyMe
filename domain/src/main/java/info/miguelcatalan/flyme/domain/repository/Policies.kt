package info.miguelcatalan.flyme.domain.repository

enum class ReadPolicy {
    CACHE_ONLY,
    READABLE_ONLY,
    READ_ALL;

    fun useCache(): Boolean {
        return this == CACHE_ONLY || this == READ_ALL
    }

    fun useReadable(): Boolean {
        return this == READABLE_ONLY || this == READ_ALL
    }
}