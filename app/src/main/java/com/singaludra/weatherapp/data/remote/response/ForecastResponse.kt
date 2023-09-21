package com.singaludra.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val weatherList: List<ForecastWeatherResponse>,
    @SerializedName("city") val cityDtoData: CityResponse
)

data class CityResponse (
    @SerializedName("country") val country: String,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("name") val cityName: String,
    @SerializedName("coord") val coordinate: CoordDto
) {
    data class CoordDto (
        @SerializedName("lat") val latitude: Double,
        @SerializedName("lon") val longitude: Double
    )
}

data class ForecastWeatherResponse (
    @SerializedName("main") val weatherData: MainDto,
    @SerializedName("weather") val weatherStatus: List<WeatherDto>,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("dt_txt") val date: String,
    @SerializedName("clouds") val cloudinessDto: CloudsDto
) {
    data class MainDto (
        @SerializedName("temp") val temp: Double,
        @SerializedName("feels_like") val feelsLike: Double,
        @SerializedName("pressure") val pressure: Double,
        @SerializedName("humidity") val humidity: Int,
    )

    data class WeatherDto (
        @SerializedName("main") val mainDescription: String,
        @SerializedName("description") val description: String
    )

    data class WindDto (
        @SerializedName("speed") val speed: Double,
    )

    data class CloudsDto (
        @SerializedName("all") val cloudiness: Int
    )
}
