package com.shkurta.weighttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.shkurta.weighttracker.ui.AppTheme
import com.shkurta.weighttracker.ui.navigation.AppNavigation
import com.shkurta.weighttracker.ui.theme.WeightTrackerTheme
import com.shkurta.weighttracker.ui.viewModel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /** Grab SettingsViewModel so we can observe the theme */
            val settingsViewModel: SettingsViewModel = hiltViewModel()

            /** Collect current theme choice as State so recomposition happens */
            val appThemeChoice by settingsViewModel.theme.collectAsState()

            /** Translate AppTheme -> Boolean for dark/light */
            val shouldUseDark = when (appThemeChoice) {
                AppTheme.DARK -> true
                AppTheme.LIGHT -> false
                AppTheme.AUTO -> isSystemInDarkTheme()
            }

            WeightTrackerTheme(
                darkTheme = shouldUseDark
            ) {
                AppNavigation()
            }
        }
    }
}