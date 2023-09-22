package com.singaludra.weatherapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singaludra.weatherapp.presentation.home.HomeScreen
import com.singaludra.weatherapp.presentation.home.HomeViewModel
import com.singaludra.weatherapp.presentation.search.SearchCityScreen
import com.singaludra.weatherapp.presentation.search.SearchCityViewModel


sealed class Screen(val route: String) {
    object HomeScreen : Screen(Routes.homeScreen)
    object SearchCityScreen : Screen(Routes.searchCityScreen)
}

object Routes {
    const val homeScreen = "Home Screen"
    const val searchCityScreen = "Search City Screen"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    startDestination: String = Screen.HomeScreen.route,
    homeViewModel: HomeViewModel,
    searchCityViewModel: SearchCityViewModel
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Screen.HomeScreen.route) {
                HomeScreen(homeViewModel) { navController.navigate(Screen.SearchCityScreen.route) }
            }
            composable(Screen.SearchCityScreen.route) {
                SearchCityScreen(navController, searchCityViewModel) {
                    navController.navigate(Screen.HomeScreen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.HomeScreen.route)
                    }
                }
            }
        }
    }
}