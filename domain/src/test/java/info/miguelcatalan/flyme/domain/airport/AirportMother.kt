package info.miguelcatalan.flyme.domain.airport

class AirportMother {
    companion object {
        private const val anyName: String = "any_name"
        private const val anyAirportCode: String = "any_airport_code"
        private val anyLocation: Location = Location(
            latitude = 0.0,
            longitude = 0.0
        )

        fun givenAnyAirport(): Airport {
            return givenAnAirport()
        }

        fun givenAnAirport(
            name: String = anyName,
            airportCode: String = anyAirportCode,
            location: Location = anyLocation
        ): Airport {
            return Airport(
                name = name,
                airportCode = airportCode,
                location = location
            )
        }

    }
}