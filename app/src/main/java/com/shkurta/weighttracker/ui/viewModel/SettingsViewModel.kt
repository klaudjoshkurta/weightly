package com.shkurta.weighttracker.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkurta.weighttracker.data.repository.SettingsRepository
import com.shkurta.weighttracker.ui.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repo: SettingsRepository
) : ViewModel() {

    val theme: StateFlow<AppTheme> =
        repo.themeFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppTheme.AUTO
        )

    fun updateTheme(appTheme: AppTheme) {
        viewModelScope.launch {
            repo.setTheme(appTheme)
        }
    }
}