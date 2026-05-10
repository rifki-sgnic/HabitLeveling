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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrifkii.habitleveling.ui.status.StatusScreen
import com.mrifkii.habitleveling.ui.status.StatusViewModel
import com.mrifkii.habitleveling.ui.quest.QuestScreen
import com.mrifkii.habitleveling.ui.quest.QuestViewModel
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.SystemBlue
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
                            0 -> player?.let {
                                StatusScreen(
                                    player = it,
                                    onIncreaseStat = { statName ->
                                        statusViewModel.increaseStat(statName)
                                    }
                                )
                            } ?: Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = SystemBlue)
                            }
                            1 -> QuestScreen(
                                quests = quests,
                                onCompleteQuest = { completedQuest ->
                                    questViewModel.completeQuest(completedQuest)
                                },
                                onGenerateAiQuests = {
                                    questViewModel.generateNewQuests()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
