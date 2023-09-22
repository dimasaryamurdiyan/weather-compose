package com.singaludra.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.singaludra.weatherapp.domain.model.Forecast

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

fun ForecastResponse.mapToDomain(): Forecast =
    Forecast(
        weatherList = this.weatherList.map {
             Forecast.ForecastWeather(
                 weatherData = Forecast.ForecastWeather.Main(
                     it.weatherData.temp,
                     it.weatherData.feelsLike,
                     it.weatherData.pressure,
                     it.weatherData.humidity
                 ),
                weatherStatus = it.weatherStatus.map { weather ->
                    Forecast.ForecastWeather.Weather(
                        mainDescription = weather.mainDescription,
                        description = weather.description
                    )
                },
                 wind = Forecast.ForecastWeather.Wind(it.wind.speed),
                 date = it.date,
                 cloudiness = Forecast.ForecastWeather.Cloudiness(it.cloudinessDto.cloudiness)
             )
        },
        cityDtoData = Forecast.City(
            cityName = this.cityDtoData.cityName,
            country = this.cityDtoData.country,
            weatherImage = 0,
            temp = this.weatherList[0].weatherData.temp,
            longitude = this.cityDtoData.coordinate.longitude,
            latitude = this.cityDtoData.coordinate.latitude,
            description = this.weatherList[0].weatherStatus[0].mainDescription
        )
    )