package com.mrifkii.habitleveling.domain.usecase

import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.repository.QuestRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuestsUseCase @Inject constructor(
    private val repository: QuestRepository
) {
    operator fun invoke(): Flow<List<Quest>> = repository.getQuests()
}
