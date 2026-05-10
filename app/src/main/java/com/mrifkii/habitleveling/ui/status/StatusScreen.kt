package com.mrifkii.habitleveling.ui.status

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.ui.components.SystemWindow
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.SystemBlue

@Composable
fun StatusScreen(
    player: Player,
    onIncreaseStat: (String) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Outer background
    ) {
        SystemWindow(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                // Header: Status title and Level
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STATUS",
                        color = SystemBlue,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 4.sp
                    )
                    
                    LevelBadge(level = player.level)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Identity Section
                IdentityInfo(player)

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = SystemBlue.copy(alpha = 0.3f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                // Vitals Section
                VitalsSection(player)

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = SystemBlue.copy(alpha = 0.3f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                // Stats Section
                StatsSection(player, onIncreaseStat)

                Spacer(modifier = Modifier.height(24.dp))

                // Currency
                GoldDisplay(gold = player.gold)
            }
        }
    }
}

@Composable
fun LevelBadge(level: Int) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .border(2.dp, SystemBlue, CircleShape)
            .padding(4.dp)
            .border(1.dp, SystemBlue.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "LV.", color = SystemBlue, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(text = level.toString(), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun IdentityInfo(player: Player) {
    Column {
        InfoRow("NAME:", player.name, isPrimary = true)
        InfoRow("JOB:", player.jobClass)
        InfoRow("TITLE:", player.title)
        InfoRow("RANK:", player.rank, color = getRankColor(player.rank))
    }
}

@Composable
fun InfoRow(label: String, value: String, isPrimary: Boolean = false, color: Color = Color.White) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(
            text = value,
            color = if (isPrimary) SystemBlue else color,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun VitalsSection(player: Player) {
    Column {
        GlowProgressBar(label = "HP", current = player.hp, max = player.maxHp, color = SystemBlue)
        Spacer(modifier = Modifier.height(12.dp))
        GlowProgressBar(label = "MP", current = player.mp, max = player.maxMp, color = Color(0xFF9C27B0))
        Spacer(modifier = Modifier.height(12.dp))
        GlowProgressBar(label = "FATIGUE", current = player.fatigue, max = 100, color = Color.Red)
    }
}

@Composable
fun GlowProgressBar(label: String, current: Int, max: Int, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = "$current / $max", color = Color.White, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color.DarkGray.copy(alpha = 0.5f))
                .border(0.5.dp, color.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
        ) {
            val progress = (current.toFloat() / max.toFloat()).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(color.copy(alpha = 0.7f), color)
                        )
                    )
                    .border(1.dp, color.copy(alpha = 0.8f), RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun StatsSection(
    player: Player,
    onIncreaseStat: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "STATS",
                color = SystemBlue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            if (player.remainingStatPoints > 0) {
                Text(
                    text = "[Points: ${player.remainingStatPoints}]",
                    color = Color.Yellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        StatItem("STRENGTH", player.stats.strength, player.remainingStatPoints > 0) {
            onIncreaseStat("STRENGTH")
        }
        StatItem("VITALITY", player.stats.vitality, player.remainingStatPoints > 0) {
            onIncreaseStat("VITALITY")
        }
        StatItem("AGILITY", player.stats.agility, player.remainingStatPoints > 0) {
            onIncreaseStat("AGILITY")
        }
        StatItem("INTELLIGENCE", player.stats.intelligence, player.remainingStatPoints > 0) {
            onIncreaseStat("INTELLIGENCE")
        }
        StatItem("PERCEPTION", player.stats.perception, player.remainingStatPoints > 0) {
            onIncreaseStat("PERCEPTION")
        }
    }
}

@Composable
fun StatItem(
    label: String,
    value: Int,
    canIncrease: Boolean,
    onIncrease: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, fontSize = 15.sp)
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value.toString(),
                color = SystemBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            
            if (canIncrease) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                    tint = SystemBlue,
                    modifier = Modifier
                        .size(20.dp)
                        .border(1.dp, SystemBlue, RoundedCornerShape(4.dp))
                        .clickable { onIncrease() }
                )
            }
        }
    }
}

@Composable
fun GoldDisplay(gold: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Yellow.copy(alpha = 0.1f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "GOLD", color = Color.Yellow, fontWeight = FontWeight.Bold)
        Text(
            text = "$gold G",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

fun getRankColor(rank: String): Color {
    return when (rank.uppercase()) {
        "S" -> Color(0xFFFFD700) // Gold
        "A" -> Color(0xFFFF4500) // OrangeRed
        "B" -> Color(0xFF9400D3) // DarkViolet
        "C" -> Color(0xFF1E90FF) // DodgerBlue
        else -> Color.White
    }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    HabitLevelingTheme {
        StatusScreen(
            player = Player(
                name = "Sung Jin-Woo",
                jobClass = "Shadow Monarch",
                title = "Wolf Slayer",
                rank = "S"
            )
        )
    }
}
