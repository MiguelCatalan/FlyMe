package info.miguelcatalan.flyme.data.airport

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type


class NamesApiDeserializer : JsonDeserializer<Names> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Names {

        val namesElement = json.asJsonObject.get("Name")

        val namesApi = when {
            namesElement.isJsonArray -> (context.deserialize(
                namesElement.asJsonArray,
                Array<NameApi>::class.java
            ) as Array<NameApi>).toList()

            namesElement.isJsonObject -> listOf(context.deserialize(namesElement.asJsonObject, NameApi::class.java))

            else -> throw JsonParseException("Unsupported type")
        }

        return Names(
            names = namesApi
        )
    }
}