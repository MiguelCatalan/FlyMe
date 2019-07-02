package info.miguelcatalan.flyme.data.schedules

import info.miguelcatalan.flyme.data.client.LufthansaApi
import info.miguelcatalan.flyme.domain.auth.Auth
import info.miguelcatalan.flyme.domain.repository.RxBaseRepository
import info.miguelcatalan.flyme.domain.repository.RxReadableDataSource
import info.miguelcatalan.flyme.domain.schedule.ScheduleOptions
import info.miguelcatalan.flyme.domain.schedule.ScheduleQuery
import io.reactivex.Observable

class AirportApiDataSource(
    private val lufthansaApi: LufthansaApi,
    private val authRepository: RxBaseRepository<String, Auth>
) : RxReadableDataSource<ScheduleQuery, ScheduleOptions> {

    override fun getByKey(key: ScheduleQuery): Observable<ScheduleOptions> {
        return authRepository.getByKey(Auth.KEY)
            .flatMap { auth ->
                lufthansaApi.getSchedules(
                    authorization = "Bearer ${auth.accessToken}",
                    departureAirportCode = key.departureAirportCode,
                    arrivalAirportCode = key.arrivalAirportCode,
                    date = key.date.toString()
                ).map { schedulesResponse ->
                    val options = schedulesResponse.scheduleResource.schedules.map { it.toDomain() }
                    ScheduleOptions(options)
                }
            }
    }

    override fun getAll(): Observable<List<ScheduleOptions>> = throw NotImplementedError()

}
