package com.mrifkii.habitleveling.data.repository

import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.repository.PlayerRepository

class PlayerRepositoryImpl(
//    private val localDataSource: PlayerDao,
//    private val remoteDataSource: GeminiApi
): PlayerRepository {
    override suspend fun getPlayerStatus(): Player {
        TODO("Not yet implemented")
    }

    override suspend fun updateXp(amount: Float) {
        TODO("Not yet implemented")
    }
}