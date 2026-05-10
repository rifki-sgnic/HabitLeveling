package com.mrifkii.habitleveling.ui.quest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.components.SystemWindow
import com.mrifkii.habitleveling.ui.quest.components.QuestItem
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.SystemBlue

@Composable
fun QuestScreen(
    quests: List<Quest>,
    onCompleteQuest: (Quest) -> Unit,
    onGenerateAiQuests: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        SystemWindow(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "DAILY QUEST",
                        color = SystemBlue,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    )
                    
                    IconButton(onClick = onGenerateAiQuests) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Generate AI Quests",
                            tint = SystemBlue
                        )
                    }
                }
                
                Text(
                    text = "[Preparation for becoming strong]",
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(quests) { quest ->
                        QuestItem(quest = quest, onComplete = onCompleteQuest)
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(80.dp)) // Padding for bottom nav
                    }
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
                Quest("1", "Push-ups", "100 times (0/100)", QuestType.DAILY, rewardXp = 20f, rewardGold = 100),
                Quest("2", "Sit-ups", "100 times (0/100)", QuestType.DAILY, rewardXp = 20f, rewardGold = 100),
                Quest("3", "Squats", "100 times (0/100)", QuestType.DAILY, rewardXp = 20f, rewardGold = 100),
                Quest("4", "Running", "10 km (0/10)", QuestType.DAILY, isCompleted = true, rewardXp = 40f, rewardGold = 200)
            ),
            onCompleteQuest = {}
        )
    }
}
