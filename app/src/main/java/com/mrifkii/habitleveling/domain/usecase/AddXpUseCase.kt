package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.repository.PlayerRepository

class AddXpUseCase(private val repository: PlayerRepository) {
    suspend operator fun invoke(xpGained: Float) {
        val currentPlayer = repository.getPlayerStatus()
        // level up logic
        repository.updateXp(currentPlayer.xp + xpGained)
    }
}