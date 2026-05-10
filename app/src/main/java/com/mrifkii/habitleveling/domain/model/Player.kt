package com.mrifkii.habitleveling.domain.model

data class Player(
    val name: String,
    val title: String = "None",
    val jobClass: String = "None",
    val level: Int = 1,
    val rank: String = "E",
    val hp: Int = 100,
    val maxHp: Int = 100,
    val mp: Int = 10,
    val maxMp: Int = 10,
    val fatigue: Int = 0,
    val xp: Float = 0f,
    val gold: Int = 0,
    val remainingStatPoints: Int = 0,
    val lastCheckIn: Long = System.currentTimeMillis(),
    val stats: PlayerStats = PlayerStats()
)

data class PlayerStats(
    val strength: Int = 10,
    val vitality: Int = 10,
    val agility: Int = 10,
    val intelligence: Int = 10,
    val perception: Int = 10
)
