package com.mrifkii.habitleveling.di

import android.content.Context
import androidx.room.Room
import com.mrifkii.habitleveling.data.local.HabitLevelingDatabase
import com.mrifkii.habitleveling.data.local.dao.PlayerDao
import com.mrifkii.habitleveling.data.local.dao.QuestDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HabitLevelingDatabase {
        return Room.databaseBuilder(
            context,
            HabitLevelingDatabase::class.java,
            HabitLevelingDatabase.DATABASE_NAME
        ).build()
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
