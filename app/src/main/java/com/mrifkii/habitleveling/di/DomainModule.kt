package com.mrifkii.habitleveling.di

import com.mrifkii.habitleveling.domain.service.LevelingService
import com.mrifkii.habitleveling.domain.usecase.CompleteQuestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLevelingService(): LevelingService {
        return LevelingService()
    }

    @Provides
    @Singleton
    fun provideCompleteQuestUseCase(levelingService: LevelingService): CompleteQuestUseCase {
        return CompleteQuestUseCase(levelingService)
    }
}
