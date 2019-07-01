package info.miguelcatalan.flyme.domain.airport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.miguelcatalan.flyme.domain.utils.value
import io.reactivex.Observable
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal to`
import org.junit.Test

class AirportResourceTest {

    private val api: AirportApi = mock()
    private val resource = AirportResource(api)

    @Test
    fun `should return Tenerife airports when filtering by capitalized name`() {
        val tfnAirport = AirportMother.givenAnAirport(name = "Tenerife Norte", airportCode = "TFN")
        val tfsAirport = AirportMother.givenAnAirport(name = "Tenerife Sur", airportCode = "TFS")
        whenever(api.getAllAirports()).thenReturn(
            Observable.just(
                listOf(
                    AirportMother.givenAnyAirport(),
                    AirportMother.givenAnyAirport(),
                    tfnAirport,
                    tfsAirport
                )
            )
        )

        val testObserver = resource.searchForAirport("Tenerife").test()

        testObserver.value().size `should equal to` 2
        testObserver.value() `should contain` tfnAirport
        testObserver.value() `should contain` tfsAirport
    }

    @Test
    fun `should return Tenerife airports when filtering by lowecased name`() {
        val tfnAirport = AirportMother.givenAnAirport(name = "Tenerife Norte", airportCode = "TFN")
        val tfsAirport = AirportMother.givenAnAirport(name = "Tenerife Sur", airportCode = "TFS")
        whenever(api.getAllAirports()).thenReturn(
            Observable.just(
                listOf(
                    AirportMother.givenAnyAirport(),
                    AirportMother.givenAnyAirport(),
                    tfnAirport,
                    tfsAirport
                )
            )
        )

        val testObserver = resource.searchForAirport("tenerife").test()

        testObserver.value().size `should equal to` 2
        testObserver.value() `should contain` tfnAirport
        testObserver.value() `should contain` tfsAirport
    }

    @Test
    fun `should return Tenerife airports when filtering by uppercase airport code`() {
        val tfnAirport = AirportMother.givenAnAirport(name = "Tenerife Norte", airportCode = "TFN")
        val tfsAirport = AirportMother.givenAnAirport(name = "Tenerife Sur", airportCode = "TFS")
        whenever(api.getAllAirports()).thenReturn(
            Observable.just(
                listOf(
                    AirportMother.givenAnyAirport(),
                    AirportMother.givenAnyAirport(),
                    tfnAirport,
                    tfsAirport
                )
            )
        )

        val testObserver = resource.searchForAirport("TF").test()

        testObserver.value().size `should equal to` 2
        testObserver.value() `should contain` tfnAirport
        testObserver.value() `should contain` tfsAirport
    }

    @Test
    fun `should return Tenerife airports when filtering by lowercase airport code`() {
        val tfnAirport = AirportMother.givenAnAirport(name = "Tenerife Norte", airportCode = "TFN")
        val tfsAirport = AirportMother.givenAnAirport(name = "Tenerife Sur", airportCode = "TFS")
        whenever(api.getAllAirports()).thenReturn(
            Observable.just(
                listOf(
                    AirportMother.givenAnyAirport(),
                    AirportMother.givenAnyAirport(),
                    tfnAirport,
                    tfsAirport
                )
            )
        )

        val testObserver = resource.searchForAirport("tf").test()

        testObserver.value().size `should equal to` 2
        testObserver.value() `should contain` tfnAirport
        testObserver.value() `should contain` tfsAirport
    }

    private fun getAnyListOfAirportsAnd(): List<Airport> {
        return listOf(
            Airport(name = "Barajas", airportCode = "MAD"),
            Airport(name = "El prat", airportCode = "BCN"),
            Airport(name = "Tenerife Norte", airportCode = "TFN"),
            Airport(name = "Tenerife Sur", airportCode = "TFS")
        )
    }

}