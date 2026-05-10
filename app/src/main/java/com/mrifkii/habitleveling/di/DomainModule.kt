package com.mrifkii.habitleveling.di

import com.mrifkii.habitleveling.data.remote.AiQuestService
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import com.mrifkii.habitleveling.domain.service.LevelingService
import com.mrifkii.habitleveling.domain.usecase.*
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

    @Provides
    @Singleton
    fun provideAddXpUseCase(
        repository: PlayerRepository,
        levelingService: LevelingService
    ): AddXpUseCase {
        return AddXpUseCase(repository, levelingService)
    }

    @Provides
    @Singleton
    fun provideCheckDailyPenaltyUseCase(
        playerRepository: PlayerRepository,
        questRepository: QuestRepository
    ): CheckDailyPenaltyUseCase {
        return CheckDailyPenaltyUseCase(playerRepository, questRepository)
    }

    @Provides
    @Singleton
    fun provideGenerateAiQuestsUseCase(
        aiQuestService: AiQuestService,
        playerRepository: PlayerRepository,
        questRepository: QuestRepository
    ): GenerateAiQuestsUseCase {
        return GenerateAiQuestsUseCase(aiQuestService, playerRepository, questRepository)
    }
}
