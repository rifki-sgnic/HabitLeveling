package com.mrifkii.habitleveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.status.StatusScreen
import com.mrifkii.habitleveling.ui.status.StatusViewModel
import com.mrifkii.habitleveling.ui.quest.QuestScreen
import com.mrifkii.habitleveling.ui.quest.QuestViewModel
import com.mrifkii.habitleveling.ui.components.BottomNav
import com.mrifkii.habitleveling.ui.components.PlayerHeader
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            viewModel<MainViewModel>()
            val statusViewModel: StatusViewModel = viewModel()
            val questViewModel: QuestViewModel = viewModel()

            val player by statusViewModel.player.collectAsState()
            val quests by questViewModel.quests.collectAsState()

            HabitLevelingTheme {
                MainContent(
                    player = player,
                    quests = quests,
                    onIncreaseStat = { statusViewModel.increaseStat(it) },
                    onCompleteQuest = { questViewModel.completeQuest(it) },
                    onGenerateAiQuests = { questViewModel.generateNewQuests() }
                )
            }
        }
    }
}

@Composable
fun MainContent(
    player: Player?,
    quests: List<Quest>,
    onIncreaseStat: (String) -> Unit,
    onCompleteQuest: (Quest) -> Unit,
    onGenerateAiQuests: () -> Unit
) {
    var currentTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            player?.let { PlayerHeader(player = it) }
        },
        bottomBar = {
            BottomNav(
                currentTab = currentTab,
                onTabSelected = { currentTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentTab) {
                0 -> player?.let {
                    StatusScreen(
                        player = it,
                        onIncreaseStat = onIncreaseStat
                    )
                } ?: Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

                1 -> QuestScreen(
                    quests = quests,
                    onCompleteQuest = onCompleteQuest,
                    onGenerateAiQuests = onGenerateAiQuests
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val dummyPlayer = Player(
        name = "Sung Jin-Woo",
        title = "Wolf Slayer",
        level = 1,
        stats = PlayerStats()
    )
    val dummyQuests = listOf(
        Quest(
            id = "1",
            title = "Daily Quest: Getting Stronger",
            description = "Complete 100 push-ups, 100 sit-ups, 100 squats, and 10km run.",
            type = QuestType.DAILY
        )
    )

    HabitLevelingTheme {
        MainContent(
            player = dummyPlayer,
            quests = dummyQuests,
            onIncreaseStat = {},
            onCompleteQuest = {},
            onGenerateAiQuests = {}
        )
    }
}
