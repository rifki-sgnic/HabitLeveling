package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.domain.service.LevelingService

class CompleteQuestUseCase(
    private val levelingService: LevelingService
) {
    operator fun invoke(player: Player, quest: Quest): Pair<Player, Quest> {
        if (quest.isCompleted) return Pair(player, quest)

        val updatedQuest = quest.copy(isCompleted = true)
        
        // Cek apakah quest ini adalah quest rank-up (tipe RANK_UP)
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

        return Pair(updatedPlayer, updatedQuest)
    }
}
