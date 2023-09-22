package com.singaludra.weatherapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.singaludra.weatherapp.domain.model.Forecast

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "temp")
    var temp: Double,

    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,

    @ColumnInfo(name = "city")
    var cityName: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "weather_image")
    var weatherImage: Int,

    @ColumnInfo(name = "description")
    var description: String,
)

fun CityEntity.mapToDomain(): Forecast.City {
    return Forecast.City (
        temp = this.temp,
        cityName = this.cityName,
        country = this.country,
        weatherImage = this.weatherImage,
        longitude = this.longitude,
        latitude = this.latitude,
        description = this.description
    )
}
