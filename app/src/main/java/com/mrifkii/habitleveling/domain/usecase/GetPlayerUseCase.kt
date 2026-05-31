package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<Player?> = repository.getPlayer()
}
