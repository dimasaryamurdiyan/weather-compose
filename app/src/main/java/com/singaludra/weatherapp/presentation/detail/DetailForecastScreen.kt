package com.singaludra.weatherapp.presentation.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.presentation.common.CurrentWeatherDetailRow
import com.singaludra.weatherapp.presentation.common.DailyWeatherRow
import com.singaludra.weatherapp.utils.HourConverter
import com.singaludra.weatherapp.utils.WeatherType
import com.singaludra.weatherapp.utils.cloudiness
import com.singaludra.weatherapp.utils.degree
import com.singaludra.weatherapp.utils.feels_like
import com.singaludra.weatherapp.utils.humidity
import com.singaludra.weatherapp.utils.pressure
import com.singaludra.weatherapp.utils.temp
import com.singaludra.weatherapp.utils.toCelcius
import com.singaludra.weatherapp.utils.wind


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailForecastScreen(
    navController: NavController, cityName: String?, viewModel: DetailForecastViewModel
){
    val forecastUIState by viewModel.forecastUIState.collectAsState()

    viewModel.getForecastData(cityName.toString())

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){
        WeatherSection(forecastUIState)
    }
}

@Composable
fun WeatherSection(forecastUIState: ForecastUIState) {
    val context = LocalContext.current
    when(forecastUIState){
        is ForecastUIState.Success -> {
            forecastUIState.forecast?.let { 
                CurrentWeatherSection(it)
                DetailsSection(forecast = it)
            }

        }
        is ForecastUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(64.dp))
            }
        }
        is ForecastUIState.Error -> {
            Toast.makeText(context, forecastUIState.errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun CurrentWeatherSection(forecast: Forecast) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 16.dp, start = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = forecast.cityDtoData.cityName,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier
                .padding(top = 48.dp),
        ) {
            Icon(
                painterResource(
                    WeatherType.setWeatherType(
                        forecast.weatherList[0].weatherStatus[0].mainDescription,
                        forecast.weatherList[0].weatherStatus[0].description,
                        HourConverter.convertHour(forecast.weatherList[0].date.substring(11, 13))),
                ),
                contentDescription = forecast.weatherList[0].weatherStatus[0].description,
                modifier = Modifier
                    .size(36.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = "${forecast.weatherList[0].weatherData.temp.toCelcius()}$degree",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.DarkGray
            )
        }

    }
}

@Composable
private fun DetailsSection(forecast: Forecast) {
    Box(
        modifier = Modifier
            .padding(top = 150.dp)
            .fillMaxSize(),
        Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ForecastSection(forecast)
            WeatherDetailSection(forecast)
        }
    }
}

@Composable
fun WeatherDetailSection(forecast: Forecast) {
    CurrentWeatherDetailRow(
        title1 = temp,
        value1 = "${forecast.weatherList[0].weatherData.temp.toCelcius()}$degree",
        title2 = feels_like,
        value2 = "${forecast.weatherList[0].weatherData.feelsLike.toCelcius()}$degree"
    )
    CurrentWeatherDetailRow(
        title1 = cloudiness,
        value1 = "${forecast.weatherList[0].cloudiness.cloudiness}%",
        title2 = humidity,
        value2 = "${forecast.weatherList[0].weatherData.humidity}%"
    )
    CurrentWeatherDetailRow(
        title1 = wind,
        value1 = "${forecast.weatherList[0].wind.speed}KM",
        title2 = pressure,
        value2 = "${forecast.weatherList[0].weatherData.pressure}"
    )
}

@Composable
fun ForecastSection(forecast: Forecast) {
    ForecastTitle(text = "Hourly Weather")
    DailyWeatherRow(forecasts = forecast.weatherList.take(8))
    ForecastTitle(text = "Daily Weather")
    DailyWeatherRow(forecasts = forecast.weatherList.takeLast(32))
}


@Composable
fun ForecastTitle(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, top = 8.dp)
            .background(MaterialTheme.colorScheme.background),

        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp)
    )
}

