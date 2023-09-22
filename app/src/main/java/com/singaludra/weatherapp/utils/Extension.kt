package com.singaludra.weatherapp.utils

fun Double.toCelcius(): String {
    val result = this - 273.5
    return String.format("%.1f", result)
}