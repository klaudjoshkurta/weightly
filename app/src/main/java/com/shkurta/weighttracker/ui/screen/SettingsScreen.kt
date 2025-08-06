package com.shkurta.weighttracker.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shkurta.weighttracker.R
import com.shkurta.weighttracker.ui.theme.fontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 18.dp),
                title = {
                    Text(
                        text = "Settings",
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp).clickable {
                            navController.popBackStack()
                        },
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {

        }
    }
}