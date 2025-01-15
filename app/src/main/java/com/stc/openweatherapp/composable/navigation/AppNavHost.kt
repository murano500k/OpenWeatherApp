package com.stc.openweatherapp.composable.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stc.openweatherapp.composable.WeatherScreen
import com.stc.openweatherapp.composable.daily.DailyWeatherScreen
import com.stc.openweatherapp.composable.navigation.Routes.DAY_INDEX
import com.stc.openweatherapp.viewmodel.WeatherViewModel


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    viewModel: WeatherViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.WEATHER
    ) {
        composable(Routes.WEATHER) {
            WeatherScreen(
                viewModel = viewModel,
                onDailyItemClick = { dayIndex ->
                    navController.navigate("${Routes.DAILY_WEATHER}/$dayIndex")
                },
                navController = navController
            )
        }

        composable(
            route = "${Routes.DAILY_WEATHER}/{${DAY_INDEX}}",
            arguments = listOf(navArgument(DAY_INDEX) { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt(DAY_INDEX) ?: 0
            DailyWeatherScreen(
                viewModel = viewModel,
                dayIndex = dayIndex,
                navController = navController
            )
        }
    }
}