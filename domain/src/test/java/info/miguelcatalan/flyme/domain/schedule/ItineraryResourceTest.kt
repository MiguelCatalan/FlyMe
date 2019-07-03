package info.miguelcatalan.flyme.domain.schedule

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.AirportMother
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import io.reactivex.Observable
import org.amshove.kluent.`should not be`
import org.junit.Before
import org.junit.Test

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

    @Test(expected = NoSuchElementException::class)
    fun `should return an exception if fails`() {
        whenever(
            airportRepository.getByKey(any())
        ).thenThrow(
            NoSuchElementException()
        )

        resource.getAirportByCode("any_code")
    }
}