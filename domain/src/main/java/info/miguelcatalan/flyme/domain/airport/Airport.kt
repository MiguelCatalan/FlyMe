package info.miguelcatalan.flyme.domain.airport

import info.miguelcatalan.flyme.domain.repository.Identifiable

data class Airport(
    val name: String,
    val airportCode: String,
    val location: Location? = null
) : Identifiable<String> {

    override val key: String
        get() = airportCode
}

data class Location(
    val latitude: Double,
    val longitude: Double
)