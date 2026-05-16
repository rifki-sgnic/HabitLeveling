package com.mrifkii.habitleveling.framework.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mrifkii.habitleveling.domain.model.Resource
import com.mrifkii.habitleveling.domain.usecase.GenerateAiQuestsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last


@HiltWorker
class QuestWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val generateAiQuestsUseCase: GenerateAiQuestsUseCase
): CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("QuestWorker", "this is a worker")
        return try {
            generateAiQuestsUseCase()
                .catch { emit(Resource.Error(it.message ?: "Unknown Error")) }
                .last()
                .let { resource ->
                    when(resource) {
                        is Resource.Success -> Result.success()
                        is Resource.Error -> Result.retry()
                        else -> Result.failure()
                    }
                }
        } catch (e: Exception) {
            Log.e("QuestWorker", e.toString())
            Result.failure()
        }
    }
}