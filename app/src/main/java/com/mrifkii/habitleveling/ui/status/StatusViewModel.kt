package com.mrifkii.habitleveling.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
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
    private val repository: PlayerRepository
) : ViewModel() {

    val uiState: StateFlow<PlayerUIState> = repository.getPlayer()
        .map { player ->
            PlayerUIState(
                isLoading = false,
                player = player
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PlayerUIState(isLoading = true)
        )



    fun increaseStat(statType: StatType) {
        viewModelScope.launch {

            val currentPlayer = uiState.value.player ?: return@launch

            if (currentPlayer.remainingStatPoints <= 0) return@launch

            val updatedStats = when (statType) {
                StatType.STRENGTH ->
                    currentPlayer.stats.copy(
                        strength = currentPlayer.stats.strength + 1
                    )

                StatType.VITALITY ->
                    currentPlayer.stats.copy(
                        vitality = currentPlayer.stats.vitality + 1
                    )

                StatType.AGILITY ->
                    currentPlayer.stats.copy(
                        agility = currentPlayer.stats.agility + 1
                    )

                StatType.INTELLIGENCE ->
                    currentPlayer.stats.copy(
                        intelligence = currentPlayer.stats.intelligence + 1
                    )

                StatType.PERCEPTION ->
                    currentPlayer.stats.copy(
                        perception = currentPlayer.stats.perception + 1
                    )
            }

            repository.updatePlayer(
                currentPlayer.copy(
                    stats = updatedStats,
                    remainingStatPoints =
                        currentPlayer.remainingStatPoints - 1
                )
            )
        }
    }
}

enum class StatType {
    STRENGTH, VITALITY, AGILITY, INTELLIGENCE, PERCEPTION
}
