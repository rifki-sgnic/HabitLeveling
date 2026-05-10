package com.mrifkii.habitleveling.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatusViewModel @Inject constructor(
    private val repository: PlayerRepository
) : ViewModel() {

    val player: StateFlow<Player?> = repository.getPlayer()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun increaseStat(statName: String) {
        viewModelScope.launch {
            val currentPlayer = player.value ?: return@launch
            if (currentPlayer.remainingStatPoints <= 0) return@launch

            val updatedStats = when (statName.uppercase()) {
                "STRENGTH" -> currentPlayer.stats.copy(strength = currentPlayer.stats.strength + 1)
                "VITALITY" -> currentPlayer.stats.copy(vitality = currentPlayer.stats.vitality + 1)
                "AGILITY" -> currentPlayer.stats.copy(agility = currentPlayer.stats.agility + 1)
                "INTELLIGENCE" -> currentPlayer.stats.copy(intelligence = currentPlayer.stats.intelligence + 1)
                "PERCEPTION" -> currentPlayer.stats.copy(perception = currentPlayer.stats.perception + 1)
                else -> currentPlayer.stats
            }

            if (updatedStats != currentPlayer.stats) {
                val updatedPlayer = currentPlayer.copy(
                    stats = updatedStats,
                    remainingStatPoints = currentPlayer.remainingStatPoints - 1
                )
                repository.updatePlayer(updatedPlayer)
            }
        }
    }
}
