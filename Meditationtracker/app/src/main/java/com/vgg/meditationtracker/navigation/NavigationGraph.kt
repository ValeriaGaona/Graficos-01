package com.vgg.meditationtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vgg.meditationtracker.ui.screens.AddSessionScreen
import com.vgg.meditationtracker.ui.screens.HomeScreen
import com.vgg.meditationtracker.ui.viewmodel.MeditationViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddSession : Screen("add_session")
}

@Composable
fun NavigationGraph(viewModel: MeditationViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(viewModel = viewModel, onAddSessionClick = { navController.navigate(Screen.AddSession.route) })
        }
        composable(Screen.AddSession.route) {
            AddSessionScreen(viewModel = viewModel, onNavigateBack = { navController.popBackStack() })
        }
    }
}
