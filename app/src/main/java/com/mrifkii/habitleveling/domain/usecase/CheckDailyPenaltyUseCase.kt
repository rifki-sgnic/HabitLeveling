package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject

class CheckDailyPenaltyUseCase @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository
) {
    suspend operator fun invoke() {
        val player = playerRepository.getPlayer().first() ?: return
        val quests = questRepository.getQuests().first()

        val lastCheckIn = Calendar.getInstance().apply { timeInMillis = player.lastCheckIn }
        val now = Calendar.getInstance()

        // Cek apakah hari ini berbeda dengan hari terakhir buka app
        val isNewDay = lastCheckIn.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR) ||
                lastCheckIn.get(Calendar.YEAR) != now.get(Calendar.YEAR)

        if (isNewDay) {
            val dailyQuests = quests.filter { it.type == QuestType.DAILY }
            val anyMissed = dailyQuests.any { !it.isCompleted }

            if (anyMissed) {
                // PENALTY! Tambah Fatigue atau munculin Penalty Quest
                val updatedPlayer = player.copy(
                    fatigue = (player.fatigue + 30).coerceAtMost(100),
                    lastCheckIn = now.timeInMillis
                )
                playerRepository.updatePlayer(updatedPlayer)
                
                // Opsional: Bisa nambahin Penalty Quest beneran ke list quest di sini nanti
            } else {
                // Aman, update hari aja
                playerRepository.updatePlayer(player.copy(lastCheckIn = now.timeInMillis))
            }

            // Reset status Daily Quests jadi belum selesai buat hari baru
            dailyQuests.forEach { quest ->
                questRepository.updateQuest(quest.copy(isCompleted = false))
            }
        }
    }
}
