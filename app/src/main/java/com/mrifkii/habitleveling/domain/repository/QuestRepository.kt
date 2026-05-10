package com.mrifkii.habitleveling.domain.repository

import com.mrifkii.habitleveling.domain.model.Quest
import kotlinx.coroutines.flow.Flow

interface QuestRepository {
    fun getQuests(): Flow<List<Quest>>
    suspend fun insertQuest(quest: Quest)
    suspend fun updateQuest(quest: Quest)
    suspend fun deleteQuest(quest: Quest)
}
