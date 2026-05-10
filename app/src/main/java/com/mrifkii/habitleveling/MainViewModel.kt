package com.mrifkii.habitleveling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import com.mrifkii.habitleveling.domain.usecase.CheckDailyPenaltyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository,
    private val checkDailyPenaltyUseCase: CheckDailyPenaltyUseCase
) : ViewModel() {

    init {
        seedDatabase()
        checkPenalty()
    }

    private fun checkPenalty() {
        viewModelScope.launch {
            checkDailyPenaltyUseCase()
        }
    }

    private fun seedDatabase() {
        viewModelScope.launch {
            val player = playerRepository.getPlayer().first()
            if (player == null) {
                playerRepository.updatePlayer(
                    Player(
                        name = "Sung Jin-Woo",
                        jobClass = "Shadow Monarch",
                        title = "Wolf Slayer",
                        rank = "S",
                        level = 1,
                        hp = 100,
                        maxHp = 100,
                        mp = 10,
                        maxMp = 10,
                        fatigue = 0,
                        gold = 0,
                        remainingStatPoints = 0,
                        lastCheckIn = System.currentTimeMillis(),
                        stats = PlayerStats()
                    )
                )
                
                val defaultQuests = listOf(
                    Quest("1", "Push-ups", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                    Quest("2", "Sit-ups", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                    Quest("3", "Squats", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                    Quest("4", "Running", "10 km", QuestType.DAILY, rewardXp = 100f, rewardGold = 200)
                )
                defaultQuests.forEach { questRepository.insertQuest(it) }
            }
        }
    }
}
