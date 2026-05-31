package com.mrifkii.habitleveling.ui.quest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.Resource
import com.mrifkii.habitleveling.domain.usecase.CompleteQuestUseCase
import com.mrifkii.habitleveling.domain.usecase.GenerateAiQuestsUseCase
import com.mrifkii.habitleveling.domain.usecase.GetPlayerUseCase
import com.mrifkii.habitleveling.domain.usecase.GetQuestsUseCase
import com.mrifkii.habitleveling.ui.model.QuestUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestViewModel @Inject constructor(
    private val getQuestsUseCase: GetQuestsUseCase,
    private val getPlayerUseCase: GetPlayerUseCase,
    private val completeQuestUseCase: CompleteQuestUseCase,
    private val generateAiQuestsUseCase: GenerateAiQuestsUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val quests: StateFlow<List<Quest>> = getQuestsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uiState: StateFlow<QuestUIState> = combine(
        getPlayerUseCase(),
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
            completeQuestUseCase(quest)
        }
    }

    fun generateNewQuests() {
        viewModelScope.launch {
            generateAiQuestsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _isLoading.value = false
                        _errorMessage.value = null
                    }
                    is Resource.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = resource.message
                    }
                    is Resource.Loading -> {
                        _isLoading.value = true
                    }
                }
            }
        }
    }
}
