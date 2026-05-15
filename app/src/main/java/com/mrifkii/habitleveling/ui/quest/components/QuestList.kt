package com.mrifkii.habitleveling.ui.quest.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.components.QuestCard

@Composable
fun QuestList(
    quests: List<Quest>,
    onCompleteQuest: (Quest) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(quests) { index, quest ->
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) { visible = true }
            val alpha by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = tween(300, delayMillis = index * 100),
                label = "StaggeredAlpha"
            )

            QuestCard(
                title = quest.title,
                meta = quest.description,
                rewardXp = quest.rewardXp.toInt(),
                isCompleted = quest.isCompleted,
                onComplete = { onCompleteQuest(quest) },
                modifier = Modifier.alpha(alpha),
                tag = if (quest.type == QuestType.RANK_UP) "RANK UP" else "DAILY ${index + 1}"
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
