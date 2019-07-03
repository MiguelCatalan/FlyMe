package info.miguelcatalan.flyme.domain.schedule

import java.util.*

class ScheduleMother {
    companion object {
        fun givenAScheduleOptionsWithTwoRightAndOneWrongItineraries(
            query: ScheduleQuery
        ): ScheduleOptions {
            return ScheduleOptions(
                options = listOf(
                    Schedule(
                        departureAirportCode = query.departureAirportCode,
                        arrivalAirportCode = query.arrivalAirportCode,
                        date = Date(),
                        flights = listOf(
                            Flight(
                                departure = AirportInfo(
                                    airportCode = query.departureAirportCode,
                                    dateTime = "any"
                                ),
                                arrival = AirportInfo(
                                    airportCode = query.arrivalAirportCode,
                                    dateTime = "any"
                                ),
                                carrier = giveAnyCarrier()
                            )
                        )
                    ),
                    Schedule(
                        departureAirportCode = "any",
                        arrivalAirportCode = "any",
                        date = Date(),
                        flights = listOf(
                            Flight(
                                departure = AirportInfo(
                                    airportCode = "any",
                                    dateTime = "any"
                                ),
                                arrival = AirportInfo(
                                    airportCode = "any",
                                    dateTime = "any"
                                ),
                                carrier = giveAnyCarrier()
                            )
                        )
                    ),
                    Schedule(
                        departureAirportCode = query.departureAirportCode,
                        arrivalAirportCode = query.arrivalAirportCode,
                        date = Date(),
                        flights = listOf(
                            Flight(
                                departure = AirportInfo(
                                    airportCode = query.departureAirportCode,
                                    dateTime = "any"
                                ),
                                arrival = AirportInfo(
                                    airportCode = query.arrivalAirportCode,
                                    dateTime = "any"
                                ),
                                carrier = giveAnyCarrier()
                            )
                        )
                    )
                )
            )
        }

        fun giveAnyCarrier(): Carrier {
            return Carrier(airlineID = "any", flightNumber = 0)
        }
    }
}