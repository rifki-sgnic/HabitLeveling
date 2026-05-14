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
import kotlinx.coroutines.flow.asStateFlow
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
    val _uiState = MutableStateFlow(QuestUIState())

    val uiState = _uiState.asStateFlow()
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
                        _uiState.value = _uiState.value.copy(

                        isLoading = false,
                        errorMessage = null
                    )}
                    is Resource.Error -> {
                        Log.d("QuestViewModel", "error")
                        _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = resource.message
                    )}
                    is Resource.Loading -> {
                        Log.d("QuestViewModel", "loading")
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}
