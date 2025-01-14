package com.stc.openweatherapp.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stc.openweatherapp.composable.daily.DailyWeatherScreen
import com.stc.openweatherapp.viewmodel.WeatherViewModel


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: WeatherViewModel = hiltViewModel() // Use the shared ViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "weather" // Ensure this is a String
    ) {
        // WeatherScreen Route
        composable("weather") {
            WeatherScreen(
                viewModel = viewModel,
                onDailyItemClick = { dayIndex ->
                    navController.navigate("daily_weather/$dayIndex")
                }
            )
        }

        // DailyWeatherScreen Route with an argument for dayIndex
        composable(
            route = "daily_weather/{dayIndex}",
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 0
            DailyWeatherScreen(
                viewModel = viewModel,
                dayIndex = dayIndex,
                navController = navController // Pass the NavController
            )
        }
    }
}
