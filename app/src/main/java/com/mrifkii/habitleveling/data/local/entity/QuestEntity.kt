package com.mrifkii.habitleveling.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val type: String,
    val isCompleted: Boolean,
    val rewardXp: Float,
    val rewardGold: Int,
    val rewardStatPoints: Int
)

fun QuestEntity.toDomain(): Quest {
    return Quest(
        id = id,
        title = title,
        description = description,
        type = QuestType.valueOf(type),
        isCompleted = isCompleted,
        rewardXp = rewardXp,
        rewardGold = rewardGold,
        rewardStatPoints = rewardStatPoints
    )
}

fun Quest.toEntity(): QuestEntity {
    return QuestEntity(
        id = id,
        title = title,
        description = description,
        type = type.name,
        isCompleted = isCompleted,
        rewardXp = rewardXp,
        rewardGold = rewardGold,
        rewardStatPoints = rewardStatPoints
    )
}
