package info.miguelcatalan.flyme.data.client

import org.junit.Test
import retrofit2.HttpException

class LufthansaApiTest {

    companion object {
        const val BASE_URL = "https://api.lufthansa.com/v1/"
        const val KEY = "kcukpydf4gmy84e3hwrcufee"
        const val SECRET = "ayVqaZPS3G"

        const val UNAUTHORIZED = 401
        const val BAD_REQUEST = 400
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
        testObserver.assertValue { auth ->
            auth.accessToken.isNotBlank() &&
                    auth.tokenType == "bearer" &&
                    auth.expiresIn > 0
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
        testObserver.assertValue { airportResponse ->
            airportResponse.airportsResource.airports.airports.size == 5
        }
    }

    @Test
    fun `should return an airport detail if the airport code exists`() {
        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).flatMap { authResponse ->
            lufthansaApi.getAirport(
                authorization = "Bearer ${authResponse.accessToken}",
                airportCode = "MAD"
            )
        }.test()

        testObserver.assertComplete()
        testObserver.assertValue { airportResponse ->
            airportResponse.airportResource.airport.airport.airportCode == "MAD"
        }
    }

    @Test
    fun `should fail if the airport code does not exists`() {
        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).flatMap { authResponse ->
            lufthansaApi.getAirport(
                authorization = "Bearer ${authResponse.accessToken}",
                airportCode = "ANY_TEXT"
            )
        }.test()

        testObserver.assertError { error ->
            (error as HttpException).code() == BAD_REQUEST
        }
    }

    @Test
    fun `should return a list of schedules`() {
        val departureAirportCode = "BCN"
        val arrivalAirportCode = "MAD"

        val testObserver = lufthansaApi.authenticate(
            key = KEY,
            secret = SECRET
        ).flatMap { authResponse ->
            lufthansaApi.getSchedules(
                authorization = "Bearer ${authResponse.accessToken}",
                departureAirportCode = departureAirportCode,
                arrivalAirportCode = arrivalAirportCode,
                date = "2019-07-20"
            )
        }.test()

        testObserver.assertComplete()
        testObserver.assertValue { schedulesResponse ->
            schedulesResponse.scheduleResource.schedules.isNotEmpty() &&
                    schedulesResponse.scheduleResource.schedules.first().let { schedule ->
                        schedule.flights.isNotEmpty()
                    }
        }
    }
}