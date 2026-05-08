package com.mrifkii.habitleveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.domain.usecase.CompleteQuestUseCase
import com.mrifkii.habitleveling.ui.status.StatusScreen
import com.mrifkii.habitleveling.ui.quest.QuestScreen
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.SystemBlue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var completeQuestUseCase: CompleteQuestUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            var player by remember {
                mutableStateOf(
                    Player(
                        name = "Sung Jin-Woo",
                        jobClass = "Shadow Monarch",
                        title = "Wolf Slayer",
                        rank = "S",
                        level = 1,
                        hp = 100,
                        maxHp = 100,
                        mp = 10,
                        maxMp = 10,
                        fatigue = 0,
                        gold = 0,
                        remainingStatPoints = 0,
                        stats = PlayerStats()
                    )
                )
            }

            var quests by remember {
                mutableStateOf(
                    listOf(
                        Quest("1", "Push-ups", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                        Quest("2", "Sit-ups", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                        Quest("3", "Squats", "100 times", QuestType.DAILY, rewardXp = 50f, rewardGold = 100),
                        Quest("4", "Running", "10 km", QuestType.DAILY, rewardXp = 100f, rewardGold = 200)
                    )
                )
            }

            var currentTab by remember { mutableIntStateOf(0) }

            HabitLevelingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.Black,
                            contentColor = SystemBlue
                        ) {
                            NavigationBarItem(
                                selected = currentTab == 0,
                                onClick = { currentTab = 0 },
                                icon = { Icon(Icons.Default.Person, contentDescription = "Status") },
                                label = { Text("Status") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = SystemBlue,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = SystemBlue,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = SystemBlue.copy(alpha = 0.1f)
                                )
                            )
                            NavigationBarItem(
                                selected = currentTab == 1,
                                onClick = { currentTab = 1 },
                                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Quests") },
                                label = { Text("Quests") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = SystemBlue,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = SystemBlue,
                                    unselectedTextColor = Color.Gray,
                                    indicatorColor = SystemBlue.copy(alpha = 0.1f)
                                )
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentTab) {
                            0 -> StatusScreen(player = player)
                            1 -> QuestScreen(
                                quests = quests,
                                onCompleteQuest = { completedQuest ->
                                    val (updatedPlayer, updatedQuest) = completeQuestUseCase(player, completedQuest)
                                    player = updatedPlayer
                                    quests = quests.map { if (it.id == updatedQuest.id) updatedQuest else it }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
