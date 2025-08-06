package com.shkurta.weighttracker.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shkurta.weighttracker.R
import com.shkurta.weighttracker.ui.theme.fontFamily
import com.shkurta.weighttracker.ui.viewModel.WeightViewModel
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: WeightViewModel = hiltViewModel()
) {
    val list by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 18.dp),
                title = {
                    Text(
                        text = "History",
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
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(list) { index, record ->
                    val prev = list.getOrNull(index + 1)
                    val diff = prev?.let { record.weight - it.weight }
                    val diffText = diff?.let {
                        when {
                            it > 0 -> "+${"%.1f".format(it)} kg"
                            it < 0 -> "-${"%.1f".format(-it)} kg"
                            else -> "No change"
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "${record.weight} kg $diffText",
                            fontFamily = fontFamily,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = DateFormat.getDateTimeInstance().format(Date(record.timestamp)),
                            fontFamily = fontFamily,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}