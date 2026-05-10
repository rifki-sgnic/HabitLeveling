package com.mrifkii.habitleveling.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats

@Entity(tableName = "player")
data class PlayerEntity(
    @PrimaryKey val id: Int = 0, // Single player for now
    val name: String,
    val title: String,
    val jobClass: String,
    val level: Int,
    val rank: String,
    val hp: Int,
    val maxHp: Int,
    val mp: Int,
    val maxMp: Int,
    val fatigue: Int,
    val xp: Float,
    val gold: Int,
    val remainingStatPoints: Int,
    val lastCheckIn: Long,
    @Embedded val stats: PlayerStatsEntity
)

data class PlayerStatsEntity(
    val strength: Int,
    val vitality: Int,
    val agility: Int,
    val intelligence: Int,
    val perception: Int
)

fun PlayerEntity.toDomain(): Player {
    return Player(
        name = name,
        title = title,
        jobClass = jobClass,
        level = level,
        rank = rank,
        hp = hp,
        maxHp = maxHp,
        mp = mp,
        maxMp = maxMp,
        fatigue = fatigue,
        xp = xp,
        gold = gold,
        remainingStatPoints = remainingStatPoints,
        lastCheckIn = lastCheckIn,
        stats = PlayerStats(
            strength = stats.strength,
            vitality = stats.vitality,
            agility = stats.agility,
            intelligence = stats.intelligence,
            perception = stats.perception
        )
    )
}

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        name = name,
        title = title,
        jobClass = jobClass,
        level = level,
        rank = rank,
        hp = hp,
        maxHp = maxHp,
        mp = mp,
        maxMp = maxMp,
        fatigue = fatigue,
        xp = xp,
        gold = gold,
        remainingStatPoints = remainingStatPoints,
        lastCheckIn = lastCheckIn,
        stats = PlayerStatsEntity(
            strength = stats.strength,
            vitality = stats.vitality,
            agility = stats.agility,
            intelligence = stats.intelligence,
            perception = stats.perception
        )
    )
}
