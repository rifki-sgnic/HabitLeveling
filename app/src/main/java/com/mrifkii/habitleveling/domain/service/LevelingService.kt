package com.mrifkii.habitleveling.domain.service

import com.mrifkii.habitleveling.domain.model.Player

class LevelingService {
    
    /**
     * System Scaling Tiers Logic
     * Menghitung XP yang dibutuhkan untuk naik dari level saat ini ke level berikutnya.
     */
    fun getRequiredXp(level: Int): Float {
        val baseHeaderXp = 100f
        return when {
            level < 5 -> baseHeaderXp + ((level - 1) * 50f)
            level < 15 -> 300f + ((level - 5) * 100f)
            level < 25 -> 1300f + ((level - 15) * 200f)
            level < 35 -> 3300f + ((level - 25) * 400f)
            level < 50 -> 7300f + ((level - 35) * 800f)
            level < 75 -> 19300f + ((level - 50) * 1500f)
            level < 90 -> 56800f + ((level - 75) * 3000f)
            else -> 101800f + ((level - 90) * 5000f)
        }
    }

    /**
     * Rank Thresholds Logic
     * Menentukan Rank berdasarkan Level saat ini.
     */
    fun calculateRank(level: Int): String {
        return when {
            level <= 25 -> "E"
            level <= 35 -> "D"
            level <= 50 -> "C"
            level <= 75 -> "B"
            level <= 90 -> "A"
            else -> "S"
        }
    }

    fun processXpGain(player: Player, xpGain: Float, hasCompletedRankUpQuest: Boolean = false): Player {
        if (player.level >= 100) return player // Max Level cap

        var currentXp = player.xp + xpGain
        var currentLevel = player.level
        var currentRank = player.rank
        var remainingStatPoints = player.remainingStatPoints
        var hp = player.hp
        var mp = player.mp
        var maxHp = player.maxHp
        var maxMp = player.maxMp

        // Logika Level Up
        // Player bisa naik level kalau:
        // 1. XP cukup DAN dia sedang tidak di level cap rank-nya
        // 2. ATAU XP cukup DAN dia baru saja menyelesaikan quest rank-up
        while (currentXp >= getRequiredXp(currentLevel) && currentLevel < 100) {
            val canProgress = !isAtRankLevelCap(currentLevel, currentRank) || hasCompletedRankUpQuest
            
            if (!canProgress) break // Mentok di level cap sampai quest rank-up kelar

            currentXp -= getRequiredXp(currentLevel)
            currentLevel++
            
            // Update Rank otomatis sesuai level baru (jika sudah melewati cap)
            currentRank = calculateRank(currentLevel)
            
            // Level Up Rewards
            remainingStatPoints += 5
            maxHp += 50
            maxMp += 20
            hp = maxHp
            mp = maxMp
        }

        return player.copy(
            level = currentLevel,
            rank = currentRank,
            xp = if (currentLevel >= 100) 0f else currentXp,
            remainingStatPoints = remainingStatPoints,
            hp = hp,
            maxHp = maxHp,
            mp = mp,
            maxMp = maxMp
        )
    }

    /**
     * Memeriksa apakah player berada di level maksimal untuk rank-nya.
     */
    fun isAtRankLevelCap(level: Int, rank: String): Boolean {
        return when (rank.uppercase()) {
            "E" -> level >= 25
            "D" -> level >= 35
            "C" -> level >= 50
            "B" -> level >= 75
            "A" -> level >= 90
            else -> false // Rank S tidak punya cap selain level 100
        }
    }
}
