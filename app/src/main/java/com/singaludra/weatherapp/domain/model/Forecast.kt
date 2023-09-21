package com.singaludra.weatherapp.domain.model

import com.singaludra.weatherapp.data.local.entity.CityEntity

data class Forecast(
    val weatherList: List<ForecastWeather>,
    val cityDtoData: City
) {
    data class ForecastWeather(
        val id: Int = 1,
        val weatherData: Main,
        val weatherStatus: List<Weather>,
        val wind: Wind,
        val date: String,
        val cloudiness: Cloudiness
    ) {
        data class Main(
            val temp: Double,
            val feelsLike: Double,
            val pressure: Double,
            val humidity: Int,
        )

        data class Weather (
            val mainDescription: String,
            val description: String
        )

        data class  Wind(
            val speed: Double,
        )

        data class Cloudiness (
            val cloudiness: Int
        )
    }
    data class City(
        var temp: Double,
        var cityName: String,
        var country: String,
        var weatherImage: Int,
        var longitude: Double,
        var latitude: Double
    )
}

fun Forecast.City.mapToEntity() : CityEntity {
    return CityEntity(
        temp = this.temp,
        cityName = this.cityName,
        country = this.country,
        weatherImage = this.weatherImage,
        longitude = this.longitude,
        latitude = this.latitude
    )
}

