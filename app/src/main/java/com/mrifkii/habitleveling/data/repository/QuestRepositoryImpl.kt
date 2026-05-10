package com.mrifkii.habitleveling.data.repository

import com.mrifkii.habitleveling.data.local.dao.QuestDao
import com.mrifkii.habitleveling.data.local.entity.toDomain
import com.mrifkii.habitleveling.data.local.entity.toEntity
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuestRepositoryImpl @Inject constructor(
    private val dao: QuestDao
) : QuestRepository {
    override fun getQuests(): Flow<List<Quest>> {
        return dao.getAllQuests().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertQuest(quest: Quest) {
        dao.insertQuest(quest.toEntity())
    }

    override suspend fun updateQuest(quest: Quest) {
        dao.updateQuest(quest.toEntity())
    }

    override suspend fun deleteQuest(quest: Quest) {
        dao.deleteQuest(quest.toEntity())
    }
}
