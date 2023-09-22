package com.singaludra.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.singaludra.weatherapp.presentation.detail.DetailForecastViewModel
import com.singaludra.weatherapp.presentation.home.HomeViewModel
import com.singaludra.weatherapp.presentation.navigation.NavGraph
import com.singaludra.weatherapp.presentation.search.SearchCityViewModel
import com.singaludra.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val searchCityViewModel: SearchCityViewModel by viewModels()
    private val detailForecastViewModel: DetailForecastViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavGraph(
                        homeViewModel = homeViewModel,
                        searchCityViewModel = searchCityViewModel,
                        detailForecastViewModel = detailForecastViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherAppTheme {
        Greeting("Android")
    }
}