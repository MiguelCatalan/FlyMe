package info.miguelcatalan.flyme.data.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import info.miguelcatalan.flyme.data.airport.Names
import info.miguelcatalan.flyme.data.airport.NamesApiDeserializer
import info.miguelcatalan.flyme.data.schedules.ScheduleApi
import info.miguelcatalan.flyme.data.schedules.ScheduleApiDeserializer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClientBuilder(
    baseUrl: String
) {

    private val gson: Gson =
        GsonBuilder()
            .registerTypeAdapter(ScheduleApi::class.java, ScheduleApiDeserializer())
            .registerTypeAdapter(Names::class.java, NamesApiDeserializer())
            .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    fun <T> create(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}