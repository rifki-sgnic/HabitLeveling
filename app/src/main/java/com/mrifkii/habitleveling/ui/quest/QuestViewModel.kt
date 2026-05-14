package com.mrifkii.habitleveling.ui.quest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.Resource
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import com.mrifkii.habitleveling.domain.usecase.CompleteQuestUseCase
import com.mrifkii.habitleveling.domain.usecase.GenerateAiQuestsUseCase
import com.mrifkii.habitleveling.ui.model.QuestUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val quests: StateFlow<List<Quest>> = questRepository.getQuests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<QuestUIState> = combine(
        playerRepository.getPlayer(),
        _isLoading,
        _errorMessage
    ) { player, isLoading, errorMessage ->
        QuestUIState(
            player = player,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = QuestUIState(isLoading = true)
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
        Log.d("QuestViewModel", "generating")
        viewModelScope.launch {
            generateAiQuestsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("QuestViewModel", "success")
                        _isLoading.value = false
                        _errorMessage.value = null
                    }
                    is Resource.Error -> {
                        Log.d("QuestViewModel", "error")
                        _isLoading.value = false
                        _errorMessage.value = resource.message
                    }
                    is Resource.Loading -> {
                        Log.d("QuestViewModel", "loading")
                        _isLoading.value = true
                    }
                }
            }
        }
    }
}
