package com.shkurta.weighttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shkurta.weighttracker.ui.navigation.AppNavigation
import com.shkurta.weighttracker.ui.theme.WeightTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeightTrackerTheme {
                AppNavigation()
            }
        }
    }
}