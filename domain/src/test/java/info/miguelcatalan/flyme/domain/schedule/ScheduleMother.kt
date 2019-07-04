package info.miguelcatalan.flyme.domain.schedule

import java.util.Date

class ScheduleMother {
    companion object {

        fun givenAnySchedule(
            departureAirportCode: String = "any",
            arrivalAirportCode: String = "any",
            date: Date = Date(),
            flights: List<Flight> = listOf(
                FlightMother.givenAnFlight()
            )
        ): Schedule {
            return Schedule(
                departureAirportCode = departureAirportCode,
                arrivalAirportCode = arrivalAirportCode,
                date = date,
                flights = flights
            )
        }

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
                                    dateTime = Date()
                                ),
                                arrival = AirportInfo(
                                    airportCode = query.arrivalAirportCode,
                                    dateTime = Date()
                                ),
                                carrier = CarrierMother.givenAnyCarrier()
                            )
                        )
                    ),
                    givenAnySchedule(),
                    Schedule(
                        departureAirportCode = query.departureAirportCode,
                        arrivalAirportCode = query.arrivalAirportCode,
                        date = Date(),
                        flights = listOf(
                            Flight(
                                departure = AirportInfo(
                                    airportCode = query.departureAirportCode,
                                    dateTime = Date()
                                ),
                                arrival = AirportInfo(
                                    airportCode = query.arrivalAirportCode,
                                    dateTime = Date()
                                ),
                                carrier = CarrierMother.givenAnyCarrier()
                            )
                        )
                    )
                )
            )
        }
    }
}

class AirportInfoMother {
    companion object {
        fun givenAnyAirportInfo(
            airportCode: String = "any",
            dateTime: Date = Date(),
            terminal: String? = null
        ): AirportInfo {
            return AirportInfo(
                airportCode = airportCode,
                dateTime = dateTime,
                terminal = terminal
            )
        }
    }
}

class FlightMother {
    companion object {
        fun givenAnFlight(
            departure: AirportInfo = AirportInfoMother.givenAnyAirportInfo(),
            arrival: AirportInfo = AirportInfoMother.givenAnyAirportInfo(),
            carrier: Carrier = CarrierMother.givenAnyCarrier()
        ): Flight {
            return Flight(
                departure = departure,
                arrival = arrival,
                carrier = carrier
            )
        }
    }
}

class CarrierMother {
    companion object {
        fun givenAnyCarrier(
            airlineID: String = "any", flightNumber: Int = 0
        ): Carrier {
            return Carrier(
                airlineID = airlineID,
                flightNumber = flightNumber
            )
        }
    }
}