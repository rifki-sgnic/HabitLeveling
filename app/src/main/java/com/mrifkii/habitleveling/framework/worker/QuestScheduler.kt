package com.mrifkii.habitleveling.framework.worker

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuestScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleAiQuests() {
        Log.d("Quest Scheduler", "this is a scheduler")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<QuestWorker>()
            .setConstraints(constraints)
            .build()

        val periodicRequest = PeriodicWorkRequestBuilder<QuestWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                "generate_ai_quests_immediate",
                ExistingWorkPolicy.KEEP,
                oneTimeRequest
            )
            enqueueUniquePeriodicWork(
                "generate_ai_quests",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicRequest
            )
        }
    }
}