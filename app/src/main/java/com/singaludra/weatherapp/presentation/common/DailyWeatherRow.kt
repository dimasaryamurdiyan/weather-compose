package com.singaludra.weatherapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.utils.HourConverter
import com.singaludra.weatherapp.utils.WeatherType
import com.singaludra.weatherapp.utils.toCelcius

@Composable
fun DailyWeatherRow(forecasts: List<Forecast.ForecastWeather>) {
    LazyRow(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) {
            if (forecasts.size == 8) {
                WeatherCard(
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    weatherIcon = WeatherType.setWeatherType(
                        it.weatherStatus[0].mainDescription,
                        it.weatherStatus[0].description,
                        HourConverter.convertHour(it.date.substring(11, 13)),
                    ),
                    degree = "${it.weatherData.temp.toCelcius()}°"
                )
            } else {
                WeatherCard(
                    date = it.date.substring(5, 10).replace('-', '/'),
                    time = HourConverter.convertHour(it.date.substring(11, 13)),
                    weatherIcon = WeatherType.setWeatherType(
                        it.weatherStatus[0].mainDescription,
                        it.weatherStatus[0].description,
                        HourConverter.convertHour(it.date.substring(11, 13)),
                    ),
                    degree = "${it.weatherData.temp.toCelcius()}°"
                )
            }
        }
    }
}

@Composable
private fun WeatherCard(date: String? = null, time: String, weatherIcon: Int, degree: String) {
    Card(
        modifier = Modifier,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
                }
                Text(text = time, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
            }
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = weatherIcon),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Text(text = degree, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
        }
    }
}