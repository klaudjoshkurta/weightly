package com.shkurta.weighttracker.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shkurta.weighttracker.R
import com.shkurta.weighttracker.ui.navigation.Screen
import com.shkurta.weighttracker.ui.theme.fontFamily
import com.shkurta.weighttracker.ui.viewModel.WeightViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: WeightViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val inputSheetState = rememberModalBottomSheetState()
    var showInputSheet by remember { mutableStateOf(false) }
    var weightInput by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val history by viewModel.history.collectAsState(initial = emptyList())
    val latest = history.firstOrNull()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Weight Tracker",
                        fontFamily = fontFamily,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
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
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    latest?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "${it.weight}",
                            textAlign = TextAlign.Center,
                            fontFamily = fontFamily,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "kg",
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            /** Actions */
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.navigate(Screen.History.route)
                        },
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(60.dp))
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            showInputSheet = true
                        },
                    painter = painterResource(id = R.drawable.ic_new),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(60.dp))
                Icon(
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.navigate(Screen.Settings.route)
                        },
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = null,
                )
            }
        }

        if (showInputSheet) {

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            ModalBottomSheet(
                onDismissRequest = { showInputSheet = false },
                sheetState = inputSheetState,
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        textStyle = TextStyle(
                            fontFamily = fontFamily,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        ),
                        decorationBox = { innerTextField ->
                            if (weightInput.isEmpty()) {
                                Text(
                                    text = "0kg",
                                    fontFamily = fontFamily,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38F),
                                    textAlign = TextAlign.Center
                                )
                            }
                            innerTextField()
                        },
                        keyboardOptions = KeyboardOptions().copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (weightInput.isNotEmpty()) {
                                    scope.launch { inputSheetState.hide() }.invokeOnCompletion {
                                        if (!inputSheetState.isVisible) {
                                            weightInput.toFloatOrNull()?.let {
                                                viewModel.addWeight(it)
                                                weightInput = ""
                                            }
                                            showInputSheet = false
                                        }
                                    }
                                }
                            }
                        )
                    )
                }
            }
        }
    }
}