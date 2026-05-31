package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import com.mrifkii.habitleveling.domain.service.LevelingService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CompleteQuestUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository,
    private val levelingService: LevelingService
) {
    suspend operator fun invoke(quest: Quest) {
        if (quest.isCompleted) return

        val player = playerRepository.getPlayer().first() ?: return
        
        // 1. Mark quest as completed
        val updatedQuest = quest.copy(isCompleted = true)
        
        // 2. Process Leveling & Rewards
        val isRankUpQuest = quest.type == QuestType.RANK_UP
        var updatedPlayer = levelingService.processXpGain(
            player = player, 
            xpGain = quest.rewardXp, 
            hasCompletedRankUpQuest = isRankUpQuest
        )

        updatedPlayer = updatedPlayer.copy(
            gold = updatedPlayer.gold + quest.rewardGold,
            remainingStatPoints = updatedPlayer.remainingStatPoints + quest.rewardStatPoints
        )

        // 3. Persist changes
        questRepository.updateQuest(updatedQuest)
        playerRepository.updatePlayer(updatedPlayer)
    }
}
