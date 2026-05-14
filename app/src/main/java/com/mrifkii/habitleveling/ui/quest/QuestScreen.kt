package com.mrifkii.habitleveling.ui.quest

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.components.QuestCard
import com.mrifkii.habitleveling.ui.components.SystemMessage
import com.mrifkii.habitleveling.ui.model.QuestUIState
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun QuestRoute(
    viewModel: QuestViewModel = hiltViewModel()
) {
    val quests by viewModel.quests.collectAsState()
    val uiState by viewModel.uiState.collectAsState()





    QuestScreen(quests, { viewModel.completeQuest(it) }, { viewModel.generateNewQuests() }, uiState)
}

@Composable
fun QuestScreen(
    quests: List<Quest>,
    onCompleteQuest: (Quest) -> Unit,
    onGenerateAiQuests: () -> Unit = {},
    uiState: QuestUIState
) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // [SYSTEM] Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DAILY QUESTS",
                    style = MaterialTheme.typography.displayMedium,
                    color = colors.primary
                )

                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    IconButton(onClick = onGenerateAiQuests) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = "GENERATE AI",
                            tint = colors.primary
                        )
                    }
                }
            }

            Text(
                text = "[ PREPARATION FOR BECOMING STRONG ]",
                style = shadowTypography.section,
                color = colors.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Igris message
            SystemMessage(
                message = "Incomplete quests will lead to penalty. Dismissal is not an option.",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(quests) { index, quest ->
                    // Staggered Glitch animation
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) { visible = true }
                    val alpha by animateFloatAsState(
                        targetValue = if (visible) 1f else 0f,
                        animationSpec = tween(600, delayMillis = index * 100),
                        label = "StaggeredAlpha"
                    )

                    QuestCard(
                        title = quest.title,
                        meta = quest.description,
                        rewardXp = quest.rewardXp.toInt(),
                        isCompleted = quest.isCompleted,
                        onComplete = { onCompleteQuest(quest) },
                        modifier = Modifier.alpha(alpha),
                        tag = "DAILY ${index + 1}"
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuestScreenPreview() {
    HabitLevelingTheme(darkTheme = true) {
        QuestScreen(
            quests = listOf(
                Quest(
                    "1",
                    "Push-ups",
                    "100 times (0/100)",
                    QuestType.DAILY,
                    rewardXp = 20f,
                    rewardGold = 100
                ),
                Quest(
                    "2",
                    "Sit-ups",
                    "100 times (0/100)",
                    QuestType.DAILY,
                    rewardXp = 20f,
                    rewardGold = 100
                ),
                Quest(
                    "3",
                    "Running",
                    "10 km (0/10)",
                    QuestType.DAILY,
                    isCompleted = true,
                    rewardXp = 40f,
                    rewardGold = 200
                )
            ),
            onCompleteQuest = {},
            uiState = QuestUIState()
        )
    }
}
