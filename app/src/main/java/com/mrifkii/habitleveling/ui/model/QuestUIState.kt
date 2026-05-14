package com.mrifkii.habitleveling.ui.model

import com.mrifkii.habitleveling.domain.model.Player

data class QuestUIState(
    val player: Player? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
