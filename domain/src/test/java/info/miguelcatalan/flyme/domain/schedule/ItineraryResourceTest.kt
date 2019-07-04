package info.miguelcatalan.flyme.domain.schedule

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.AirportDetailNotFoundError
import info.miguelcatalan.flyme.domain.airport.AirportMother
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.utils.value
import io.reactivex.Observable
import org.amshove.kluent.`should equal to`
import org.amshove.kluent.`should not be`
import org.junit.Before
import org.junit.Test
import java.util.Date

class ItineraryResourceTest {

    private lateinit var resource: ItineraryResource
    private val airportRepository: RxBaseRepository<String, Airport> = mock()
    private val schedulesRepository: RxBaseRepository<ScheduleQuery, ScheduleOptions> = mock()

    @Before
    fun setUp() {
        resource = ItineraryResource(airportRepository, schedulesRepository)
    }

    @Test
    fun `should return an Airport`() {
        whenever(
            airportRepository.getByKey(any())
        ).thenReturn(
            Observable.just(AirportMother.givenAnyAirport())
        )

        val result = resource.getAirportByCode("any_code")

        result `should not be` null
    }

    @Test
    fun `should return an airport with just the code if fails`() {
        val airportCode = "MAD"
        whenever(
            airportRepository.getByKey(any())
        ).thenReturn(
            Observable.error(AirportDetailNotFoundError())
        )

        val airport = resource.getAirportByCode(airportCode)

        airport.airportCode `should equal to` airportCode
    }

    @Test
    fun `should ignore schedules if origin and destination are not the same has requested`() {
        val query = ScheduleQuery(
            departureAirportCode = "BCN",
            arrivalAirportCode = "MAD",
            date = Date()
        )
        val scheduleOptions = ScheduleMother.givenAScheduleOptionsWithTwoRightAndOneWrongItineraries(query)
        whenever(
            airportRepository.getByKey(any())
        ).thenReturn(
            Observable.error(AirportDetailNotFoundError())
        )
        whenever(
            schedulesRepository.getByKey(any())
        ).thenReturn(
            Observable.just(scheduleOptions)
        )

        val testObserver = resource.getItineraries(query).test()

        testObserver.value().size `should equal to` 2
    }
}