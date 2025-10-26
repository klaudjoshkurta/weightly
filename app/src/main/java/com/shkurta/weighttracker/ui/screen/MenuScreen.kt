package com.shkurta.weighttracker.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shkurta.weighttracker.R

enum class AppLanguage(val label: String) {
    ENGLISH("English"),
    GREEK("Ελληνικά")
}

enum class AppTheme(val label: String) {
    LIGHT("Light"),
    DARK("Dark"),
    AUTO("Auto")
}

@Composable
fun MenuScreen(
    navController: NavHostController
) {
    // current selected values (in prod, read these from ViewModel / DataStore)
    var selectedLanguage by rememberSaveable { mutableStateOf(AppLanguage.ENGLISH) }
    var selectedTheme by rememberSaveable { mutableStateOf(AppTheme.AUTO) }

    // dialog visibility
    var showLanguageDialog by rememberSaveable { mutableStateOf(false) }
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(vertical = 24.dp)) {

            // LANGUAGE ROW
            SettingsRow(
                title = "Language",
                value = selectedLanguage.label,
                onClick = { showLanguageDialog = true }
            )

            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            // THEME ROW
            SettingsRow(
                title = "Theme",
                value = selectedTheme.label,
                onClick = { showThemeDialog = true }
            )
        }

        // LANGUAGE DIALOG
        if (showLanguageDialog) {
            LanguageDialog(
                current = selectedLanguage,
                onSelect = { newLang ->
                    selectedLanguage = newLang
                    showLanguageDialog = false
                },
                onDismiss = { showLanguageDialog = false }
            )
        }

        // THEME DIALOG
        if (showThemeDialog) {
            ThemeDialog(
                current = selectedTheme,
                onSelect = { newTheme ->
                    selectedTheme = newTheme
                    showThemeDialog = false
                },
                onDismiss = { showThemeDialog = false }
            )
        }
    }
}


/**
 * One row in the settings list.
 * Left side: title + current value
 * Right side: chevron icon
 */
@Composable
private fun SettingsRow(
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        }

        // trailing chevron (replace with your own icon in res/drawable)
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
    }
}

/**
 * Dialog to choose language.
 * Uses RadioButton list inside AlertDialog.
 */
@Composable
fun LanguageDialog(
    current: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /* we select instantly on tap so no confirm button needed */ },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(
                text = "Language",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                LanguageOptionRow(
                    label = AppLanguage.ENGLISH.label,
                    selected = current == AppLanguage.ENGLISH,
                    onClick = { onSelect(AppLanguage.ENGLISH) }
                )
                LanguageOptionRow(
                    label = AppLanguage.GREEK.label,
                    selected = current == AppLanguage.GREEK,
                    onClick = { onSelect(AppLanguage.GREEK) }
                )
            }
        }
    )
}

@Composable
private fun LanguageOptionRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .weight(1f),
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}

/**
 * Dialog to choose theme mode.
 * Light / Dark / Auto.
 */
@Composable
fun ThemeDialog(
    current: AppTheme,
    onSelect: (AppTheme) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ThemeOptionRow(
                    label = AppTheme.LIGHT.label,
                    description = "Always use light colors",
                    selected = current == AppTheme.LIGHT,
                    onClick = { onSelect(AppTheme.LIGHT) }
                )
                ThemeOptionRow(
                    label = AppTheme.DARK.label,
                    description = "Always use dark colors",
                    selected = current == AppTheme.DARK,
                    onClick = { onSelect(AppTheme.DARK) }
                )
                ThemeOptionRow(
                    label = AppTheme.AUTO.label,
                    description = "Follow system setting",
                    selected = current == AppTheme.AUTO,
                    onClick = { onSelect(AppTheme.AUTO) }
                )
            }
        }
    )
}

@Composable
private fun ThemeOptionRow(
    label: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textAlign = TextAlign.Start
            )
        }

        RadioButton(
            selected = selected,
            onClick = onClick
        )
    }
}