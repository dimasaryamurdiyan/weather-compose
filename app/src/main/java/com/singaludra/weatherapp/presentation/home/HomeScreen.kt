package com.singaludra.weatherapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.singaludra.weatherapp.domain.model.Forecast
import com.singaludra.weatherapp.presentation.common.CityWeatherCard
import com.singaludra.weatherapp.presentation.navigation.Screen
import com.singaludra.weatherapp.utils.degree
import com.singaludra.weatherapp.utils.toCelcius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel, onNavigateToSearchCityScreen: () -> Unit) {
    val myCitiesState by viewModel.myCitiesState.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                title = { Text(text = "Weather", style = MaterialTheme.typography.headlineMedium) },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    onNavigateToSearchCityScreen()
                })
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
            }

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            HomeContent(
                viewModel = viewModel,
                myCitiesState = myCitiesState,
                navController
            )
        }
    }
}

@Composable
fun HomeContent(viewModel: HomeViewModel, myCitiesState: MyCitiesState, navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.Start
    ) {
        when (myCitiesState) {
            is MyCitiesState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        Modifier
                            .size(64.dp)
                            .align(Alignment.Center)
                            .padding(8.dp)
                    )
                }
            }
            is MyCitiesState.Success -> {
                if (myCitiesState.forecast.isNullOrEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No data found")
                    }
                } else {
                    CityListSection(myCitiesState.forecast, viewModel, navController)
                }
            }
            is MyCitiesState.Error -> {
                Toast.makeText(context, myCitiesState.errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun CityListSection(cityList: List<Forecast.City>, viewModel: HomeViewModel, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(cityList) {
            CityWeatherCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 16.dp),
                degree = "${it.temp.toCelcius()}$degree",
                city = it.cityName,
                country = it.country,
                description = it.description,
                weatherImage = it.weatherImage,
                onClick = { navController.navigate("detail".plus("/${it.cityName}"))}
            )
        }
    }
}



