package com.mrifkii.habitleveling.ui.quest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.theme.SystemBlue

@Composable
fun QuestItem(
    quest: Quest,
    onComplete: (Quest) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (quest.isCompleted) Color.DarkGray.copy(alpha = 0.3f) else Color.DarkGray.copy(alpha = 0.1f))
            .border(
                width = 1.dp,
                color = if (quest.isCompleted) Color.Gray else SystemBlue.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !quest.isCompleted) { onComplete(quest) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    color = if (quest.isCompleted) Color.Gray else Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = quest.description,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row {
                    RewardBadge(text = "${quest.rewardXp.toInt()} XP", color = SystemBlue)
                    if (quest.rewardGold > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        RewardBadge(text = "${quest.rewardGold} G", color = Color.Yellow)
                    }
                }
            }

            if (quest.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    tint = Color.Green,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun RewardBadge(text: String, color: Color) {
    Box(
        modifier = Modifier
            .border(0.5.dp, color, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}
