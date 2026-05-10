package com.mrifkii.habitleveling.data.local.dao

import androidx.room.*
import com.mrifkii.habitleveling.data.local.entity.QuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: QuestEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuests(quests: List<QuestEntity>)

    @Delete
    suspend fun deleteQuest(quest: QuestEntity)

    @Update
    suspend fun updateQuest(quest: QuestEntity)
}
