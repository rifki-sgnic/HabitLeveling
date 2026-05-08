package com.mrifkii.habitleveling.domain.service

import com.mrifkii.habitleveling.domain.model.Player

class LevelingService {
    fun getRequiredXp(level: Int): Float {
        return level * 100f
    }

    fun processXpGain(player: Player, xpGain: Float): Player {
        var currentXp = player.xp + xpGain
        var currentLevel = player.level
        var remainingStatPoints = player.remainingStatPoints
        var hp = player.hp
        var mp = player.mp

        while (currentXp >= getRequiredXp(currentLevel)) {
            currentXp -= getRequiredXp(currentLevel)
            currentLevel++
            remainingStatPoints += 5
            // Level up restores vitals
            hp = player.maxHp
            mp = player.maxMp
        }

        return player.copy(
            level = currentLevel,
            xp = currentXp,
            remainingStatPoints = remainingStatPoints,
            hp = hp,
            mp = mp
        )
    }
}
