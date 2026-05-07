package com.mrifkii.habitleveling.domain.repository

import com.mrifkii.habitleveling.domain.model.Player

interface PlayerRepository {
    suspend fun getPlayerStatus(): Player
    suspend fun updateXp(amount: Float)
}