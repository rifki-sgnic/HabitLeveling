package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.service.LevelingService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddXpUseCase @Inject constructor(
    private val repository: PlayerRepository,
    private val levelingService: LevelingService
) {
    suspend operator fun invoke(xpGained: Float) {
        val currentPlayer = repository.getPlayer().first() ?: return
        val updatedPlayer = levelingService.processXpGain(
            player = currentPlayer, 
            xpGain = xpGained,
            hasCompletedRankUpQuest = false
        )
        repository.updatePlayer(updatedPlayer)
    }
}