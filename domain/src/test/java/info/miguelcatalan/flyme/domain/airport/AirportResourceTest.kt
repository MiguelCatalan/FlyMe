package info.miguelcatalan.flyme.domain.airport

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.utils.value
import io.reactivex.Observable
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should equal to`
import org.junit.Test

class AirportResourceTest {

    private val repository: RxBaseRepository<String, Airport> = mock()
    private val resource = AirportResource(repository)

    @Test
    fun `should return Tenerife airports when filtering by capitalized name`() {
        val tfnAirport = AirportMother.givenAnAirport(name = "Tenerife Norte", airportCode = "TFN")
        val tfsAirport = AirportMother.givenAnAirport(name = "Tenerife Sur", airportCode = "TFS")
        whenever(repository.getAll()).thenReturn(
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
        whenever(repository.getAll()).thenReturn(
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
        whenever(repository.getAll()).thenReturn(
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
        whenever(repository.getAll()).thenReturn(
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
}