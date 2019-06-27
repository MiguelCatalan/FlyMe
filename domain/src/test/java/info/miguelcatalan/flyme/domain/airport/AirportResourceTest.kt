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
    fun shouldReturnTenerifeAirportsWhenFilteringByName() {
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
    fun shouldReturnTenerifeAirportsWhenFilteringByCode() {
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

}