package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.data.remote.AiQuestService
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GenerateAiQuestsUseCase @Inject constructor(
    private val aiQuestService: AiQuestService,
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository
) {
    suspend operator fun invoke() {
        val player = playerRepository.getPlayer().first() ?: return
        val previousQuests = questRepository.getQuests().first()
        
        val newQuests = aiQuestService.generateQuests(player, previousQuests)
        
        if (newQuests.isNotEmpty()) {
            // Replace old daily quests with new ones
            // In a real app, you might want to archive them or only replace if it's a new day
            newQuests.forEach { quest ->
                questRepository.insertQuest(quest)
            }
        }
    }
}
