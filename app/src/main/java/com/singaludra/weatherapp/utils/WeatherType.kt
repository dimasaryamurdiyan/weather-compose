package com.singaludra.weatherapp.utils

import androidx.annotation.DrawableRes
import com.singaludra.weatherapp.R

sealed class WeatherType(
    val weatherDescription: String,
    @DrawableRes val id: Int
) {
    object ClearSkyDay : WeatherType(
        weatherDescription = WeatherConditions.CLEAR_SKY,
        id = R.drawable.ic_clear_sky_day_48
    )

    object ClearSkyNight : WeatherType(
        weatherDescription = WeatherConditions.CLEAR_SKY,
        id = R.drawable.ic_clear_sky_night_24
    )

    object FewClouds : WeatherType(
        weatherDescription = WeatherConditions.FEW_CLOUDS,
        id = R.drawable.ic_scattered_clouds_24
    )

    object ScatteredClouds : WeatherType(
        weatherDescription = WeatherConditions.SCATTERED_CLOUDS,
        id = R.drawable.ic_scattered_clouds_24
    )

    object BrokenCloudsDay : WeatherType(
        weatherDescription = WeatherConditions.BROKEN_CLOUDS,
        id = R.drawable.ic_cloudy_day_24dp
    )

    object BrokenCloudsNight : WeatherType(
        weatherDescription = WeatherConditions.BROKEN_CLOUDS,
        id = R.drawable.ic_cloudy_night_24
    )

    object ShowerRai : WeatherType(
        weatherDescription = WeatherConditions.SHOWER_RAIN,
        id = R.drawable.ic_rain_24
    )

    object Rain : WeatherType(
        weatherDescription = WeatherConditions.RAIN,
        id = R.drawable.ic_rain_24
    )

    object Thunderstorm : WeatherType(
        weatherDescription = WeatherConditions.THUNDERSTORM,
        id = R.drawable.ic_storm_24
    )

    object Snow : WeatherType(
        weatherDescription = WeatherConditions.SNOW,
        id = R.drawable.ic_snow_24
    )

    object MistDay : WeatherType(
        weatherDescription = WeatherConditions.MIST,
        id = R.drawable.ic_mist_24
    )

    object MistNight : WeatherType(
        weatherDescription = WeatherConditions.MIST,
        id = R.drawable.ic_mist_24
    )

    companion object {
        fun setWeatherType(
            mainDescription: String,
            weatherDescription: String,
            hour: String
        ): Int {
            when (mainDescription) {
                MainWeatherConditions.CLOUDS -> {
                    return if (weatherDescription == ScatteredClouds.weatherDescription) {
                        ScatteredClouds.id
                    } else if (weatherDescription == FewClouds.weatherDescription) {
                        FewClouds.id
                    } else {
                        if (checkTime(hour)) {
                            BrokenCloudsNight.id
                        } else {
                            BrokenCloudsDay.id
                        }
                    }
                }
                MainWeatherConditions.RAIN -> {
                    return if (weatherDescription == ShowerRai.weatherDescription) {
                        if (checkTime(hour)) {
                            ShowerRai.id
                        } else {
                            ShowerRai.id
                        }
                    } else {
                        Rain.id
                    }
                }
                MainWeatherConditions.THUNDERSTORM -> {
                    return Thunderstorm.id
                }
                MainWeatherConditions.SNOW -> {
                    return Snow.id
                }
                MainWeatherConditions.CLEAR -> {
                    return if (checkTime(hour)) {
                        ClearSkyNight.id
                    } else {
                        ClearSkyDay.id
                    }
                }
                else -> {
                    return if (checkTime(hour)) {
                        MistNight.id
                    } else {
                        MistDay.id
                    }
                }
            }
        }

        // false -> day
        // true -> night
        private fun checkTime(hour: String): Boolean {
            return if ((hour.substring(0, 2) == "00" || hour.substring(0, 2) == "03" || hour.substring(0, 2) == "06") && hour.substring(3, 5) == "AM") {
                true
            } else if (hour.substring(0, 2) == "12" && hour.substring(3, 5) == "AM") {
                true
            } else (hour.substring(0, 2) == "09") && hour.substring(3, 5) == "PM"
        }
    }
}