package com.mrifkii.habitleveling.ui.quest

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.components.PlayerHeader
import com.mrifkii.habitleveling.ui.components.SystemMessage
import com.mrifkii.habitleveling.ui.model.QuestUIState
import com.mrifkii.habitleveling.ui.quest.components.QuestCategoryList
import com.mrifkii.habitleveling.ui.quest.components.QuestList
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun QuestRoute(
    viewModel: QuestViewModel = hiltViewModel()
) {
    val quests by viewModel.quests.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        uiState.player?.let { player ->
            PlayerHeader(player)
            QuestScreen(
                quests = quests,
                onCompleteQuest = { viewModel.completeQuest(it) },
                onGenerateAiQuests = { viewModel.generateNewQuests() },
                uiState = uiState
            )
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
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
    
    var selectedType by remember { mutableStateOf<QuestType?>(null) }

    // Intercept back button to return to Quest Hub
    BackHandler(enabled = selectedType != null) {
        selectedType = null
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = paddingValues.calculateBottomPadding(),
                    top = 24.dp // Use our own top padding instead of Scaffold's
                )
        ) {
            // [SYSTEM] Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (selectedType != null) {
                        IconButton(onClick = { selectedType = null }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "BACK",
                                tint = colors.primary
                            )
                        }
                    }
                    Text(
                        text = selectedType?.name?.replace("_", " ") ?: "QUEST HUB",
                        style = MaterialTheme.typography.displayMedium,
                        color = colors.primary
                    )
                }

                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
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
                text = if (selectedType == null) "[ SELECT MISSION CATEGORY ]" else "[ MISSION IN PROGRESS ]",
                style = shadowTypography.section,
                color = colors.primary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Igris message
            SystemMessage(
                message = if (selectedType == QuestType.RANK_UP)
                    "This is the path of the Monarch. Failure is death."
                else 
                    "Incomplete quests will lead to penalty. Dismissal is not an option.",
                modifier = Modifier.padding(bottom = 24.dp)
            )

            AnimatedContent(
                targetState = selectedType,
                label = "QuestNavigation"
            ) { type ->
                if (type == null) {
                    QuestCategoryList(
                        quests = quests,
                        onTypeSelected = { selectedType = it }
                    )
                } else {
                    QuestList(
                        quests = quests.filter { it.type == type },
                        onCompleteQuest = onCompleteQuest
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestScreenPreview() {
    HabitLevelingTheme {
        QuestScreen(
            quests = listOf(
                Quest("1", "Push-ups", "100 times", QuestType.DAILY),
                Quest("2", "Rank Up: Arise", "Defeat the shadows", QuestType.RANK_UP)
            ),
            onCompleteQuest = {},
            uiState = QuestUIState()
        )
    }
}
