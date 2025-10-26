package com.shkurta.weighttracker.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shkurta.weighttracker.ui.screen.HistoryScreen
import com.shkurta.weighttracker.ui.screen.HomeScreen
import com.shkurta.weighttracker.ui.screen.MenuScreen
import com.shkurta.weighttracker.ui.screen.NewRecordScreen
import com.shkurta.weighttracker.ui.screen.SettingsScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object History : Screen("history")
    data object NewRecord : Screen("new_record")
    data object Menu : Screen("menu")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController)
        }
        composable(Screen.NewRecord.route) {
            NewRecordScreen(navController)
        }
        composable(Screen.Menu.route) {
            MenuScreen(navController)
        }
    }
}