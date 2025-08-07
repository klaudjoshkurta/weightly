package com.shkurta.weighttracker.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shkurta.weighttracker.data.local.entity.WeightEntry
import com.shkurta.weighttracker.data.repository.WeightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val repo: WeightRepository
) : ViewModel() {

    val history: StateFlow<List<WeightEntry>> = repo.weightHistory()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addWeight(value: Float) {
        viewModelScope.launch { repo.addWeight(value) }
    }

    fun deleteWeight(entry: WeightEntry) {
        viewModelScope.launch { repo.deleteWeight(entry) }
    }
}