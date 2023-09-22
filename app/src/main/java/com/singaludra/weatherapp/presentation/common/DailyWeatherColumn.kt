package com.singaludra.weatherapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.singaludra.weatherapp.R
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.ui.theme.WeatherAppTheme
import com.singaludra.weatherapp.utils.HourConverter
import com.singaludra.weatherapp.utils.WeatherType
import com.singaludra.weatherapp.utils.toCelcius


@Composable
fun DailyWeatherColumn(forecasts: List<Forecast.ForecastWeather>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(forecasts) {
            WeatherColumnCard(
                date = it.date.substring(5, 10).replace('-', '/'),
                time = HourConverter.convertHour(it.date.substring(11, 13)),
                weatherImage = WeatherType.setWeatherType(
                    it.weatherStatus[0].mainDescription,
                    it.weatherStatus[0].description,
                    HourConverter.convertHour(it.date.substring(11, 13)),
                ),
                degreeMin = "${it.weatherData.temp.toCelcius()}°C",
                degreeMax = "${it.weatherData.temp.toCelcius()}°C",
                description = "a"
            )
        }
    }
}


@Composable
fun WeatherColumnCard(
    modifier: Modifier = Modifier,
    degreeMin: String,
    degreeMax: String,
    date: String?,
    time: String,
    description: String,
    weatherImage: Int,
    onClick: () -> Unit = {},
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 16.dp)
        ){
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(2f), horizontalAlignment = Alignment.Start) {
                if (date != null) {
                    Text(text = date, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
                }
                Text(text = time, style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp))
            }
            Icon(
                painterResource(weatherImage),
                contentDescription = description,
                modifier = Modifier.size(24.dp)
            )
            Text(modifier = Modifier.padding(horizontal = 8.dp) ,text = degreeMin)
            Text(modifier = Modifier.padding(horizontal = 8.dp) ,text = degreeMax)
        }
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDataCard() {
    WeatherAppTheme {
        CityWeatherCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            degree = "90",
            city = "Jakarta",
            country = "ID",
            description = "asdas",
            weatherImage = R.drawable.ic_clear_sky_day_48,
            onClick = { }
        )
    }
}