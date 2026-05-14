package com.mrifkii.habitleveling.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mrifkii.habitleveling.data.local.dao.PlayerDao
import com.mrifkii.habitleveling.data.local.dao.QuestDao
import com.mrifkii.habitleveling.data.local.entity.PlayerEntity
import com.mrifkii.habitleveling.data.local.entity.QuestEntity

@Database(
    entities = [PlayerEntity::class, QuestEntity::class],
    version = 2,
    exportSchema = false
)
abstract class HabitLevelingDatabase : RoomDatabase() {
    abstract val playerDao: PlayerDao
    abstract val questDao: QuestDao

    companion object {
        const val DATABASE_NAME = "habit_leveling_db"
    }
}
