package com.mrifkii.habitleveling.domain.repository

import com.mrifkii.habitleveling.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    fun getPlayer(): Flow<Player?>
    suspend fun updatePlayer(player: Player)
}
