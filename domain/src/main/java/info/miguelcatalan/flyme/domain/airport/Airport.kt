package info.miguelcatalan.flyme.domain.airport

import info.miguelcatalan.flyme.domain.repository.Identifiable
import java.io.Serializable

data class Airport(
    val name: String,
    val airportCode: String,
    val location: Location? = null
) : Identifiable<String>, Serializable {

    companion object {
        fun empty(
            name: String = "Missing name",
            airportCode: String = "Missing code",
            location: Location? = null
        ): Airport {
            return Airport(
                name = name,
                airportCode = airportCode,
                location = location
            )
        }
    }

    override val key: String
        get() = airportCode
}

data class Location(
    val latitude: Double,
    val longitude: Double
) : Serializable