package com.mrifkii.habitleveling.domain.usecase


import android.util.Log
import com.mrifkii.habitleveling.data.remote.AiQuestService
import com.mrifkii.habitleveling.domain.model.Resource
import com.mrifkii.habitleveling.domain.repository.PlayerRepository
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GenerateAiQuestsUseCase @Inject constructor(
    private val aiQuestService: AiQuestService,
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository
) {
     operator fun invoke(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        try {
            val player = playerRepository.getPlayer().first() ?: return@flow
            val previousQuests = questRepository.getQuests().first()

            val newQuests = aiQuestService.generateQuests(player, previousQuests)

            if (newQuests.isNotEmpty()) {
                // Replace old daily quests with new ones
                // In a real app, you might want to archive them or only replace if it's a new day
                newQuests.forEach { quest ->
                    questRepository.insertQuest(quest)
                }
            }
            emit(Resource.Success(Unit))
        } catch (e: UnknownHostException) {
            Log.e("AI_QUEST", "Error host", e)
            emit(Resource.Error("No Internet Connection"))

        } catch (e: SocketTimeoutException) {
            Log.e("AI_QUEST", "Error timeout", e)
            emit(Resource.Error("Request Timed Out"))

        } catch (e: Exception) {
            Log.e("AI_QUEST", "Error exception", e)
            emit(Resource.Error(
                e.message.toString()
            ))
        }
    }
}
