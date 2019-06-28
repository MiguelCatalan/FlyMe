package info.miguelcatalan.flyme.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable


open class RxBaseRepository<KEY, ENTITY : Identifiable<KEY>>(
    private val rxCacheableDataSources: List<RxCacheableDataSource<KEY, ENTITY>> = listOf(),
    private val rxWriteableDataSources: List<RxWriteableDataSource<KEY, ENTITY>> = listOf(),
    private val rxReadableDataSources: List<RxReadableDataSource<KEY, ENTITY>> = listOf()
) : RxReadableDataSource<KEY, ENTITY>, RxWriteableDataSource<KEY, ENTITY> {

    override fun getByKey(key: KEY): Observable<ENTITY> = getByKey(key, ReadPolicy.READ_ALL)

    fun getByKey(key: KEY, readPolicy: ReadPolicy): Observable<ENTITY> =
        Observable.concat(
            getByKeyFromDataSources(key, rxCacheableDataSources).applyReadPolicyCondition(readPolicy.useCache()),
            getByKeyFromDataSources(key, rxReadableDataSources).applyReadPolicyCondition(readPolicy.useReadable())
                .flatMap { readableValue ->
                    addOrUpdateInDataSources(readableValue, rxCacheableDataSources)
                        .switchIfEmpty(Observable.just(readableValue))
                }
        ).firstElement()
            .toObservable()


    override fun getAll(): Observable<List<ENTITY>> = getAll(ReadPolicy.READ_ALL)

    fun getAll(readPolicy: ReadPolicy): Observable<List<ENTITY>> =

        Observable.concat(
            getAllFromDataSources(rxCacheableDataSources).applyReadPolicyConditionOnList(readPolicy.useCache()),
            getAllFromDataSources(rxReadableDataSources).applyReadPolicyConditionOnList(readPolicy.useReadable())
                .flatMap { readableValue ->
                    replaceAllInDataSources(readableValue, rxCacheableDataSources)
                        .switchIfEmpty(Observable.just(readableValue))
                }
        ).firstElement()
            .toObservable()

    override fun addOrUpdate(value: ENTITY): Observable<ENTITY> =
        addOrUpdateInDataSources(value, rxWriteableDataSources)
            .concatWith(addOrUpdateInDataSources(value, rxCacheableDataSources))

    override fun replaceAll(values: List<ENTITY>): Observable<List<ENTITY>> =
        replaceAllInDataSources(values, rxWriteableDataSources)
            .concatWith(replaceAllInDataSources(values, rxCacheableDataSources))

    override fun deleteByKey(key: KEY): Completable =
        deleteByKeyFromDataSources(key, rxWriteableDataSources)
            .concatWith(deleteByKeyFromDataSources(key, rxCacheableDataSources))

    override fun deleteAll(): Completable =
        deleteAllFromDataSources(rxWriteableDataSources)
            .concatWith(deleteAllFromDataSources(rxCacheableDataSources))

    private fun getByKeyFromDataSources(key: KEY, dataSources: List<RxReadableDataSource<KEY, ENTITY>>): Observable<ENTITY> =
        Observable.fromIterable(dataSources.reversed())
            .concatMap { it.getByKey(key) }
            .firstElement()
            .toObservable()

    private fun getAllFromDataSources(dataSources: List<RxReadableDataSource<KEY, ENTITY>>): Observable<List<ENTITY>> =
        Observable.fromIterable(dataSources.reversed())
            .concatMap { it.getAll() }
            .firstElement()
            .toObservable()

    private fun addOrUpdateInDataSources(value: ENTITY, dataSources: List<RxWriteableDataSource<KEY, ENTITY>>): Observable<ENTITY> =
        Observable.fromIterable(dataSources.reversed())
            .concatMap { it.addOrUpdate(value) }

    private fun replaceAllInDataSources(values: List<ENTITY>, dataSources: List<RxWriteableDataSource<KEY, ENTITY>>): Observable<List<ENTITY>> =
        Observable.fromIterable(dataSources.reversed())
            .concatMap { it.replaceAll(values) }

    private fun deleteByKeyFromDataSources(key: KEY, dataSources: List<RxWriteableDataSource<KEY, ENTITY>>): Completable =
        Observable.fromIterable(dataSources.reversed())
            .flatMapCompletable { it.deleteByKey(key) }

    private fun deleteAllFromDataSources(dataSources: List<RxWriteableDataSource<KEY, ENTITY>>): Completable =
        Observable.fromIterable(dataSources.reversed())
            .flatMapCompletable { it.deleteAll() }

    private fun Observable<ENTITY>.applyReadPolicyCondition(condition: Boolean): Observable<ENTITY> {
        return if (condition)
            this
        else
            Observable.empty()
    }

    private fun Observable<List<ENTITY>>.applyReadPolicyConditionOnList(condition: Boolean): Observable<List<ENTITY>> {
        return if (condition)
            this
        else
            Observable.empty()
    }

}

/**
 * Represents an object that can be identified uniquely by an object of the parametrized class.
 * @param <K> The class of the key used to identify objects of this class.
 */
interface Identifiable<K> {
    val key: K
}
