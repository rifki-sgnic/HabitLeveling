package com.mrifkii.habitleveling.di

import com.mrifkii.habitleveling.data.repository.PlayerRepositoryImpl
import com.mrifkii.habitleveling.data.repository.QuestRepositoryImpl
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(
        playerRepositoryImpl: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    @Singleton
    abstract fun bindQuestRepository(
        questRepositoryImpl: QuestRepositoryImpl
    ): QuestRepository
}
