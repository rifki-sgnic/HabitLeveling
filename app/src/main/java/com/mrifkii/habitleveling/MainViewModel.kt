package com.mrifkii.habitleveling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrifkii.habitleveling.domain.usecase.CheckDailyPenaltyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkDailyPenaltyUseCase: CheckDailyPenaltyUseCase
) : ViewModel() {

    init {
        checkPenalty()
    }

    private fun checkPenalty() {
        viewModelScope.launch {
            checkDailyPenaltyUseCase()
        }
    }


}
