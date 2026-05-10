package com.mrifkii.habitleveling.domain.model

enum class QuestType {
    DAILY, EMERGENCY, SPECIAL, JOB_CHANGE, PENALTY
}

data class Quest(
    val id: String,
    val title: String,
    val description: String,
    val type: QuestType,
    val isCompleted: Boolean = false,
    val rewardXp: Float = 0f,
    val rewardGold: Int = 0,
    val rewardStatPoints: Int = 0
)
