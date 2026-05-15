package com.mrifkii.habitleveling.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.service.LevelingService
import com.mrifkii.habitleveling.domain.usecase.GetPlayerUseCase
import com.mrifkii.habitleveling.domain.usecase.IncreaseStatUseCase
import com.mrifkii.habitleveling.ui.model.PlayerUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val increaseStatUseCase: IncreaseStatUseCase,
    private val levelingService: LevelingService
) : ViewModel() {

    val uiState: StateFlow<PlayerUIState> = getPlayerUseCase()
        .map { player ->
            PlayerUIState(
                isLoading = false,
                player = player,
                isAtLevelCap = player?.let { levelingService.isAtRankLevelCap(it.level, it.rank) } ?: false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlayerUIState(isLoading = true)
        )

    fun increaseStat(statType: StatType) {
        viewModelScope.launch {
            increaseStatUseCase(statType)
        }
    }
}

enum class StatType {
    STRENGTH, VITALITY, AGILITY, INTELLIGENCE, PERCEPTION
}
