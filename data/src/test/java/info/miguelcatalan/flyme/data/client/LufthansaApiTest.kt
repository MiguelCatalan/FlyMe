package info.miguelcatalan.flyme.data.client

import org.junit.Test
import retrofit2.HttpException

class LufthansaApiTest {

    companion object {
        const val BASE_URL = "https://api.lufthansa.com/v1/"
        const val KEY = "kcukpydf4gmy84e3hwrcufee"
        const val SECRET = "ayVqaZPS3G"

        const val UNAUTHORIZED = 401
    }

    private val lufthansaApi = ApiClientBuilder(BASE_URL)
        .create(LufthansaApi::class.java)

    @Test
    fun `should fail auth when credentials are wrong`() {
        val testObserver = lufthansaApi.authenticate(
            key = "",
            secret = SECRET
        ).test()

        testObserver.assertError { error ->
            (error as HttpException).code() == UNAUTHORIZED
        }
    }

    @Test
    fun `should return a token when credentials are right`() {
        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).test()

        testObserver.assertComplete()
        testObserver.assertValue {
            it.accessToken.isNotBlank() &&
                    it.tokenType == "bearer" &&
                    it.expiresIn > 0
        }
    }

    @Test
    fun `should return five airports`() {
        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).flatMap { auth ->
            lufthansaApi.getAirports(
                authorization = "Bearer ${auth.accessToken}",
                max = 5,
                offset = 0
            )
        }.test()

        testObserver.assertComplete()
        testObserver.assertValue {
            it.airportsResource.airports.airports.size == 5
        }
    }
}