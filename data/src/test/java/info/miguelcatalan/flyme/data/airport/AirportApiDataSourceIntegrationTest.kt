package info.miguelcatalan.flyme.data.airport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.miguelcatalan.flyme.data.client.ApiClientBuilder
import info.miguelcatalan.flyme.data.client.LufthansaApi
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import io.reactivex.Observable
import org.amshove.kluent.`should equal to`
import org.junit.Before
import org.junit.Test

class AirportApiDataSourceIntegrationTest {

    companion object {
        const val BASE_URL = "https://api.lufthansa.com/v1/"
        const val KEY = "kcukpydf4gmy84e3hwrcufee"
        const val SECRET = "ayVqaZPS3G"
        const val TOTAL_AIRPORTS = 1465
    }

    private val lufthansaApi = ApiClientBuilder(BASE_URL)
        .create(LufthansaApi::class.java)

    private val authRepository: RxBaseRepository<String, Auth> = mock()

    @Before
    fun setUp() {
        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).test()

        val authToken = testObserver.values().first().accessToken
        whenever(authRepository.getByKey(Auth.KEY)).thenReturn(Observable.just(Auth(authToken, 10000)))
    }

    @Test
    fun shouldReturnAllAirportsPaginated() {
        val airportApiDataSource = AirportApiDataSource(lufthansaApi, authRepository)

        val testObserver = airportApiDataSource.getAll().test()

        testObserver.values().first().size `should equal to`  TOTAL_AIRPORTS
    }
}