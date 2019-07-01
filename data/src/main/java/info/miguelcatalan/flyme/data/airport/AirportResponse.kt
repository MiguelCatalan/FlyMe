package info.miguelcatalan.flyme.data.airport

import com.google.gson.annotations.SerializedName
import info.miguelcatalan.flyme.domain.airport.Airport
import info.miguelcatalan.flyme.domain.airport.Location

data class AirportResponse(
    @SerializedName("AirportResource")
    val airportResource: AirportResource
)

data class AirportResource(
    @SerializedName("Airports")
    val airport: SingleAirports
)

data class SingleAirports(
    @SerializedName("Airport")
    val airport: AirportApi
)

data class AirportApi(
    @SerializedName("AirportCode")
    val airportCode: String,

    @SerializedName("Position")
    val position: PositionApi?,

    @SerializedName("Names")
    val names: Names
) {

    fun toDomain(): Airport = Airport(
        airportCode = airportCode,
        name = names.names.find { it.languageCode == "en" }?.i18n ?: "",
        location = position?.coordinate?.let {
            Location(it.latitude, it.longitude)
        }
    )
}


data class PositionApi(
    @SerializedName("Coordinate")
    val coordinate: CoordinateApi
)

data class CoordinateApi(
    @SerializedName("Latitude")
    val latitude: Double,

    @SerializedName("Longitude")
    val longitude: Double
)

data class Names(
    @SerializedName("Name")
    val names: List<NameApi>
)

data class NameApi(
    @SerializedName("@LanguageCode")
    val languageCode: String,

    @SerializedName("$")
    val i18n: String
)