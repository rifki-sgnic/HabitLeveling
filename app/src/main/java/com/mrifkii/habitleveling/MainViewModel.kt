package com.mrifkii.habitleveling

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.usecase.CheckDailyPenaltyUseCase
import com.mrifkii.habitleveling.framework.worker.QuestScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkDailyPenaltyUseCase: CheckDailyPenaltyUseCase,
    private val questScheduler: QuestScheduler
) : ViewModel() {

    init {
        checkPenalty()

    }

    fun launchWorker() {
        questScheduler.scheduleAiQuests()
        Log.d("MainViewModel", "this is main")
    }
    private fun checkPenalty() {
        viewModelScope.launch {
            checkDailyPenaltyUseCase()
        }
    }


}
