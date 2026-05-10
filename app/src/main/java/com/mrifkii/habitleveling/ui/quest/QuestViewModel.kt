package com.mrifkii.habitleveling.ui.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import com.mrifkii.habitleveling.domain.usecase.CompleteQuestUseCase
import com.mrifkii.habitleveling.domain.usecase.GenerateAiQuestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val playerRepository: PlayerRepository,
    private val completeQuestUseCase: CompleteQuestUseCase,
    private val generateAiQuestsUseCase: GenerateAiQuestsUseCase
) : ViewModel() {

    val quests: StateFlow<List<Quest>> = questRepository.getQuests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun completeQuest(quest: Quest) {
        viewModelScope.launch {
            val player = playerRepository.getPlayer().first() ?: return@launch
            val (updatedPlayer, updatedQuest) = completeQuestUseCase(player, quest)
            
            playerRepository.updatePlayer(updatedPlayer)
            questRepository.updateQuest(updatedQuest)
        }
    }

    fun generateNewQuests() {
        viewModelScope.launch {
            generateAiQuestsUseCase()
        }
    }
}
