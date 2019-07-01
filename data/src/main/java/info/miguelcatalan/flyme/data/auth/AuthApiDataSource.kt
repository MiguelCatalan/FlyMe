package info.miguelcatalan.flyme.data.auth

import info.miguelcatalan.flyme.data.client.LufthansaApi
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxReadableDataSource
import io.reactivex.Observable

class AuthApiDataSource(
    private val lufthansaApi: LufthansaApi,
    private val lufthansaApiKey: String,
    private val lufthansaApiSecret: String
) : RxReadableDataSource<String, Auth> {

    override fun getByKey(key: String): Observable<Auth> =
        lufthansaApi.authenticate(
            key = lufthansaApiKey,
            secret = lufthansaApiSecret
        ).map { it.toDomain() }

    override fun getAll(): Observable<List<Auth>> = Observable.empty()
}