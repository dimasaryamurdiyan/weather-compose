package com.singaludra.weatherapp.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.singaludra.weatherapp.R
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.presentation.common.CityWeatherCard
import com.singaludra.weatherapp.utils.HourConverter
import com.singaludra.weatherapp.utils.WeatherType
import com.singaludra.weatherapp.utils.degree
import com.singaludra.weatherapp.utils.toCelcius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCityScreen(navController: NavController, viewModel: SearchCityViewModel, onNavigateToHomeScreen: () -> Unit) {
    val searchCityState by viewModel.searchCityUIState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = { TopBarSection(onNavigateToHomeScreen) },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            SearchCityScreenContent(
                viewModel = viewModel,
                searchCityState = searchCityState,
                navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSection(onBackClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Text(text = "Search", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchField(viewModel: SearchCityViewModel) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = viewModel.searchFieldValue,
        onValueChange = { viewModel.updateSearchField(it) },
        label = {
            Text(text = "Search city")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        maxLines = 1,
        trailingIcon = {
            IconButton(onClick = { viewModel.searchCityClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_24),
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun SearchCityScreenContent(
    viewModel: SearchCityViewModel,
    searchCityState: SearchCityUIState,
    navController: NavController
) {
    SearchField(viewModel)
    if (viewModel.isCitySearched) {
        when (searchCityState) {
            is SearchCityUIState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(LocalConfiguration.current.screenWidthDp.dp / 3)
                            .padding(top = 16.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            is SearchCityUIState.Success -> {
                if (searchCityState.forecast != null) {
                    WantedCityWeatherSection(searchCityState.forecast, viewModel, navController)
                }
            }
            is SearchCityUIState.Error -> {
                //
            }
        }
    }
}

@Composable
private fun WantedCityWeatherSection(forecast: Forecast, viewModel: SearchCityViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        CityWeatherCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(top = 16.dp),
            degree = "${forecast.weatherList[0].weatherData.temp.toCelcius()}$degree",
            city = forecast.cityDtoData.cityName,
            country = forecast.cityDtoData.country,
            description = forecast.weatherList[0].weatherStatus[0].description,
            weatherImage = WeatherType.setWeatherType(
                forecast.weatherList[0].weatherStatus[0].mainDescription,
                forecast.weatherList[0].weatherStatus[0].description,
                HourConverter.convertHour(forecast.weatherList[0].date.substring(11, 13)),
            ),
            onClick = {
                viewModel.addCity(
                    Forecast.City(
                        temp = forecast.weatherList[0].weatherData.temp,
                        latitude = forecast.cityDtoData.latitude,
                        longitude = forecast.cityDtoData.longitude,
                        cityName = forecast.cityDtoData.cityName,
                        country = forecast.cityDtoData.country,
                        description = forecast.cityDtoData.description,
                        weatherImage = WeatherType.setWeatherType(
                            forecast.weatherList[0].weatherStatus[0].mainDescription,
                            forecast.weatherList[0].weatherStatus[0].description,
                            HourConverter.convertHour(forecast.weatherList[0].date.substring(11, 13)),
                        ),
                    )
                )
                navController.navigateUp()
            }
        )
    }
}