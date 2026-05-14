package com.mrifkii.habitleveling.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mrifkii.habitleveling.data.local.HabitLevelingDatabase
import com.mrifkii.habitleveling.data.local.dao.PlayerDao
import com.mrifkii.habitleveling.data.local.dao.QuestDao
import com.mrifkii.habitleveling.data.local.entity.PlayerEntity
import com.mrifkii.habitleveling.data.local.entity.PlayerStatsEntity
import com.mrifkii.habitleveling.data.local.entity.QuestEntity
import com.mrifkii.habitleveling.domain.model.QuestType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HabitLevelingDatabase {

        lateinit var database: HabitLevelingDatabase
        database = Room.databaseBuilder(
            context,
            HabitLevelingDatabase::class.java,
            HabitLevelingDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .addCallback(object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                CoroutineScope(Dispatchers.IO).launch {
                    database.playerDao.insertPlayer(
                        PlayerEntity(
                            name = "Sung Jin-Woo",
                            jobClass = "Shadow Monarch",
                            title = "Weakest Hunter",
                            rank = "E",
                            level = 1,
                            hp = 100,
                            maxHp = 100,
                            mp = 10,
                            maxMp = 10,
                            fatigue = 0,
                            gold = 0,
                            remainingStatPoints = 0,
                            lastCheckIn = System.currentTimeMillis(),
                            stats = PlayerStatsEntity(
                                strength = 0,
                                vitality = 0,
                                agility = 0,
                                intelligence = 0,
                                perception = 0
                            ),
                            xp = 0f
                        )
                    )

                    database.questDao.insertQuests(
                        listOf(
                            QuestEntity(
                                "1",
                                "Push-ups",
                                "100 times",
                                QuestType.DAILY.name,
                                rewardXp = 50f,
                                rewardGold = 100,
                                isCompleted = false,
                                rewardStatPoints = 0,
                                createdAt = System.currentTimeMillis()
                            ),
                            QuestEntity(
                                "2",
                                "Sit-ups",
                                "100 times",
                                QuestType.DAILY.name,
                                rewardXp = 50f,
                                rewardGold = 100,
                                isCompleted = false,
                                rewardStatPoints = 0,
                                createdAt = System.currentTimeMillis()
                            ),
                            QuestEntity(
                                "3",
                                "Squats",
                                "100 times",
                                QuestType.DAILY.name,
                                rewardXp = 50f,
                                rewardGold = 100,
                                isCompleted = false,
                                rewardStatPoints = 0,
                                createdAt = System.currentTimeMillis()
                            ),
                            QuestEntity(
                                "4",
                                "Running",
                                "10 km",
                                QuestType.DAILY.name,
                                rewardXp = 100f,
                                rewardGold = 200,
                                isCompleted = false,
                                rewardStatPoints = 0,
                                createdAt = System.currentTimeMillis()
                            )
                        )
                    )
                }
            }
        })
            .build()

        return database
    }

    @Provides
    fun providePlayerDao(database: HabitLevelingDatabase): PlayerDao {
        return database.playerDao
    }

    @Provides
    fun provideQuestDao(database: HabitLevelingDatabase): QuestDao {
        return database.questDao
    }
}
