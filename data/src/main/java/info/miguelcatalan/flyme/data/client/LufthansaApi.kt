package info.miguelcatalan.flyme.data.client

import info.miguelcatalan.flyme.data.airport.AirportResponse
import info.miguelcatalan.flyme.data.airport.AirportsResponse
import info.miguelcatalan.flyme.data.auth.AuthResponse
import info.miguelcatalan.flyme.data.schedules.SchedulesResponse
import io.reactivex.Observable
import retrofit2.http.*

interface LufthansaApi {

    @POST("oauth/token")
    @FormUrlEncoded
    fun authenticate(
        @Field("client_id") key: String,
        @Field("client_secret") secret: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): Observable<AuthResponse>

    @GET("references/airports")
    @Headers("Accept: application/json")
    fun getAirports(
        @Header("Authorization") authorization: String,
        @Query("limit") max: Int,
        @Query("offset") offset: Int
    ): Observable<AirportsResponse>

    @GET("references/airports/{airportCode}")
    @Headers("Accept: application/json")
    fun getAirport(
        @Header("Authorization") authorization: String,
        @Path("airportCode") airportCode: String
    ): Observable<AirportResponse>

    @GET("operations/schedules/{departureAirportCode}/{arrivalAirportCode}/{date}")
    @Headers("Accept: application/json")
    fun getSchedules(
        @Header("Authorization") authorization: String,
        @Path("departureAirportCode") departureAirportCode: String,
        @Path("arrivalAirportCode") arrivalAirportCode: String,
        @Path("date") date: String
    ): Observable<SchedulesResponse>
}