package com.shkurta.weighttracker.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shkurta.weighttracker.R
import com.shkurta.weighttracker.ui.components.WeightRecord
import com.shkurta.weighttracker.ui.navigation.Screen
import com.shkurta.weighttracker.ui.theme.fontFamily
import com.shkurta.weighttracker.ui.viewModel.WeightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: WeightViewModel = hiltViewModel()
) {
    val historySheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showHistorySheet by remember { mutableStateOf(false) }
    /** Screen height in dp so we can compute 90% */
    val configuration = LocalConfiguration.current
    val maxHeightDp = remember(configuration) {
        configuration.screenHeightDp.dp * 0.9f
    }

    val history by viewModel.history.collectAsState(initial = emptyList())
    val list by viewModel.history.collectAsState()

    val latest = history.firstOrNull()
    val displayWeight = latest?.weight ?: "0.0"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            /** Latest weight in */
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$displayWeight${stringResource(R.string.kg)}",
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            /** Log Snapshot */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 24.dp)
                    .weight(1F),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (history.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.no_records_yet),
                        fontFamily = fontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
                else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.most_recent),
                                fontFamily = fontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        itemsIndexed(history.take(3)) { index, record ->
                            val prev = history.getOrNull(index + 1)
                            val diff = prev?.let { record.weight - it.weight }

                            WeightRecord(
                                record = record,
                                diff = diff ?: 0F,
                                gain = (diff ?: 0F) > 0,
                                onDelete = { viewModel.deleteWeight(record) }
                            )
                        }
                    }
                }
            }

            /** Bottom Actions */
            BottomActions(
                onAddNew = { navController.navigate(Screen.NewRecord.route) },
                onHistory = { showHistorySheet = true },
                onMenu = {}
            )
        }

        if (showHistorySheet) {

            ModalBottomSheet(
                onDismissRequest = { showHistorySheet = false },
                sheetState = historySheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(
                            max = maxHeightDp
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.history),
                        fontFamily = fontFamily,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        itemsIndexed(list) { index, record ->
                            val prev = list.getOrNull(index + 1)
                            val diff = prev?.let { record.weight - it.weight }

                            WeightRecord(
                                record = record,
                                diff = diff ?: 0F,
                                gain = (diff ?: 0F) > 0,
                                onDelete = { viewModel.deleteWeight(record) }
                            )
                        }
                    }
                }
            }
        }

        
    }
}

@Composable
fun BottomActions(
    modifier: Modifier = Modifier,
    onAddNew: () -> Unit,
    onHistory: () -> Unit,
    onMenu: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 38.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onHistory
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chart),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(38.dp))
            Button(
                onClick = onAddNew,
                modifier = Modifier
                    .width(120.dp)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(38.dp))
            IconButton(
                onClick = onMenu
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}