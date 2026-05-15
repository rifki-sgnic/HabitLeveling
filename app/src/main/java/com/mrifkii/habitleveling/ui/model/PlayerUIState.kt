package com.mrifkii.habitleveling.ui.model

import com.mrifkii.habitleveling.domain.model.Player

data class PlayerUIState(
    val isLoading: Boolean = false,
    val player: Player? = null,
    val isAtLevelCap: Boolean = false,
    val errorMessage: String? = null,
)
