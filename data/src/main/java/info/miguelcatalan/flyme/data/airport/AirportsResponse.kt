package info.miguelcatalan.flyme.data.airport

import com.google.gson.annotations.SerializedName


data class AirportsResponse(
    @SerializedName("AirportResource")
    val airportsResource: AirportsResource
)

data class AirportsResource(
    @SerializedName("Airports")
    val airports: AirportsApi,

    @SerializedName("Meta")
    val meta: Meta
)

data class AirportsApi(
    @SerializedName("Airport")
    val airports: List<AirportApi>
)

data class Meta(
    @SerializedName("TotalCount")
    val totalCount: Int
)