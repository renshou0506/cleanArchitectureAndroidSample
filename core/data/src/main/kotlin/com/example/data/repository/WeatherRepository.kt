package com.example.data.repository

import android.content.Context
import android.util.Log
import com.squareup.moshi.Json
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    @ApplicationContext val application: Context,
    private val weatherService: WeatherService,
) : WeatherRepositoryIF {
    override fun weather(): Flow<WeatherResponse> =
        flow {
            try {
                emit(
                    weatherService.weather("https://weather.tsukumijima.net/api/forecast/city/400010")
                        .execute().body() ?: WeatherResponse()
                )
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                Log.e("WeatherRepository", "fun weather", e)
                Unit
            }
        }.flowOn(Dispatchers.IO)
}


interface WeatherRepositoryIF {
    fun weather(): Flow<WeatherResponse>
}

interface WeatherService {
    //    @POST
//    fun weather(@Url url:String,@Body body:WeatherRequest): Call<WeatherResponse>
    @GET
    fun weather(@Url url: String): Call<WeatherResponse>
}


data class WeatherResponse(
    @Json(name = "publicTime")
    var publicTime: String? = "",
    @Json(name = "publicTimeFormatted")
    var publicTimeFormatted: String? = "",
    @Json(name = "publishingOffice")
    var publishingOffice: String? = "",
    @Json(name = "title")
    var title: String? = "",
    @Json(name = "link")
    var link: String? = "",
    @Json(name = "description")
    var description: Description? = null,
    @Json(name = "forecasts")
    var forecasts: List<Forecasts> = emptyList(),
)

data class Description(
    @Json(name = "publicTime")
    var publicTime: String? = "",
    @Json(name = "publicTimeFormatted")
    var publicTimeFormatted: String? = "",
    @Json(name = "headlineText")
    var headlineText: String? = "",
    @Json(name = "bodyText")
    var bodyText: String? = "",
)

data class Forecasts(
    @Json(name = "date")
    var date: String? = "",
    @Json(name = "dateLabel")
    var dateLabel: String? = "",
    @Json(name = "telop")
    var telop: String = "",
    @Json(name = "detail")
    var detail: Detail,
    @Json(name = "temperature")
    var temperature: Temperature,
    @Json(name = "chanceOfRain")
    var chanceOfRain: ChanceOfRain,
    @Json(name = "image")
    var image: Image,
)

data class Detail(
    @Json(name = "weather")
    var weather: String? = "",
    @Json(name = "wind")
    var wind: String? = "",
    @Json(name = "wave")
    var wave: String? = "",
)

data class Temperature(
    @Json(name = "min")
    var min: Min,
    @Json(name = "max")
    var max: Max,
)

data class Min(
    @Json(name = "celsius")
    var celsius: String? = "",
    @Json(name = "fahrenheit")
    var fahrenheit: String? = "",
)

data class Max(
    @Json(name = "celsius")
    var celsius: String? = "",
    @Json(name = "fahrenheit")
    var fahrenheit: String? = "",
)

data class ChanceOfRain(
    @Json(name = "T00_06")
    var T00_06: String? = "",
    @Json(name = "T06_12")
    var T06_12: String? = "",
    @Json(name = "T12_18")
    var T12_18: String? = "",
    @Json(name = "T18_24")
    var T18_24: String? = "",
)

data class Image(

    @Json(name = "title")
    var title: String? = "",
    @Json(name = "url")
    var url: String? = "",
    @Json(name = "width")
    var width: Int? = 0,
    @Json(name = "height")
    var height: Int? = 0,
)
