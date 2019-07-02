package info.miguelcatalan.flyme.data.schedules

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ScheduleApiDeserializer : JsonDeserializer<ScheduleApi> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ScheduleApi {

        val flightElement = json.asJsonObject.get("Flight")
        val flightApi = when {
            flightElement.isJsonArray -> (context.deserialize(
                flightElement.asJsonArray,
                Array<FlightApi>::class.java
            ) as Array<FlightApi>).toList()

            flightElement.isJsonObject -> listOf(context.deserialize(flightElement.asJsonObject, FlightApi::class.java))

            else -> throw JsonParseException("Unsupported type of monument element")
        }

        return ScheduleApi(
            flights = flightApi
        )
    }
}