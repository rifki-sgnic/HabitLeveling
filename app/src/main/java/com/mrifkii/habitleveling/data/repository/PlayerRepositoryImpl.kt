package com.mrifkii.habitleveling.data.repository

import com.mrifkii.habitleveling.data.local.dao.PlayerDao
import com.mrifkii.habitleveling.data.local.entity.toDomain
import com.mrifkii.habitleveling.data.local.entity.toEntity
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val dao: PlayerDao
) : PlayerRepository {
    override fun getPlayer(): Flow<Player?> {
        return dao.getPlayer().map { it?.toDomain() }
    }

    override suspend fun updatePlayer(player: Player) {
        dao.insertPlayer(player.toEntity())
    }
}
