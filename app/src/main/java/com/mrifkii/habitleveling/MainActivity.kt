package com.mrifkii.habitleveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.ui.status.StatusScreen
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitLevelingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        StatusScreen(
                            player = Player(
                                name = "Sung Jin-Woo",
                                jobClass = "Shadow Monarch",
                                title = "Wolf Slayer",
                                rank = "S",
                                level = 100,
                                hp = 12000,
                                maxHp = 15000,
                                mp = 8000,
                                maxMp = 10000,
                                fatigue = 20,
                                gold = 500000,
                                stats = PlayerStats(
                                    strength = 250,
                                    vitality = 200,
                                    agility = 230,
                                    intelligence = 180,
                                    perception = 150
                                ),
                                remainingStatPoints = 5
                            )
                        )
                    }
                }
            }
        }
    }
}
