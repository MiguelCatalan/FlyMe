package info.miguelcatalan.flyme.domain.airport

data class Airport(
    val name: String,
    val airportCode: String,
    val location: Location? = null
)

data class Location(
    val latitude: Double,
    val longitude: Double
)