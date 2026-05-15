package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.ui.status.StatType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IncreaseStatUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(statType: StatType) {
        val player = repository.getPlayer().first() ?: return
        if (player.remainingStatPoints <= 0) return

        val updatedStats = when (statType) {
            StatType.STRENGTH -> player.stats.copy(strength = player.stats.strength + 1)
            StatType.VITALITY -> player.stats.copy(vitality = player.stats.vitality + 1)
            StatType.AGILITY -> player.stats.copy(agility = player.stats.agility + 1)
            StatType.INTELLIGENCE -> player.stats.copy(intelligence = player.stats.intelligence + 1)
            StatType.PERCEPTION -> player.stats.copy(perception = player.stats.perception + 1)
        }

        repository.updatePlayer(
            player.copy(
                stats = updatedStats,
                remainingStatPoints = player.remainingStatPoints - 1
            )
        )
    }
}
