package com.mrifkii.habitleveling.ui.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.ui.components.HudPanel
import com.mrifkii.habitleveling.ui.components.PlayerHeader
import com.mrifkii.habitleveling.ui.components.StatBar
import com.mrifkii.habitleveling.ui.components.SystemMessage
import com.mrifkii.habitleveling.ui.status.components.InfoRow
import com.mrifkii.habitleveling.ui.status.components.RankUpWarning
import com.mrifkii.habitleveling.ui.status.components.StatRadar
import com.mrifkii.habitleveling.ui.status.components.StatRow
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun StatusRoute(
    viewModel: StatusViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val player = uiState.player

    when {
        uiState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        player != null -> {
            Column(modifier = Modifier.fillMaxSize()) {
                PlayerHeader(player)
                StatusScreen(
                    player = player, 
                    isAtLevelCap = uiState.isAtLevelCap,
                    onIncreaseStat = { viewModel.increaseStat(it) }
                )
            }
        }
    }
}

@Composable
fun StatusScreen(
    player: Player,
    isAtLevelCap: Boolean,
    onIncreaseStat: (StatType) -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    val shadowColors = LocalShadowColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        // [SHADOW GUIDE] message
        SystemMessage(
            message = "Performance stable. Recommend prioritizing Agility for current quest set.",
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Rank-Up Warning (UI Feedback)
        RankUpWarning(isAtLevelCap, player.rank)

        // Identity Panel
        HudPanel(tag = "PLAYER IDENTITY", isActive = true) {
            InfoRow("NAME", player.name, isPrimary = true)
            InfoRow("JOB", player.jobClass)
            InfoRow("TITLE", player.title)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hexagonal Stat Radar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            StatRadar(stats = player.stats)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Vitals Panel
        HudPanel(tag = "VITALS", isActive = true) {
            StatBar(
                icon = Icons.Outlined.Favorite,
                label = "HP",
                current = player.hp,
                max = player.maxHp,
                color = shadowColors.hp.color
            )
            StatBar(
                icon = Icons.Outlined.FlashOn,
                label = "MP",
                current = player.mp,
                max = player.maxMp,
                color = shadowColors.mp.color
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Detailed Stats Panel
        HudPanel(tag = "ATTRIBUTES", isActive = true) {
            if (player.remainingStatPoints > 0) {
                Text(
                    text = "AVAILABLE POINTS: ${player.remainingStatPoints}",
                    style = shadowTypography.section,
                    color = shadowColors.warning.color,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            StatRow("STR", player.stats.strength, player.remainingStatPoints > 0) {
                onIncreaseStat(StatType.STRENGTH)
            }
            StatRow("VIT", player.stats.vitality, player.remainingStatPoints > 0) {
                onIncreaseStat(StatType.VITALITY)
            }
            StatRow("AGI", player.stats.agility, player.remainingStatPoints > 0) {
                onIncreaseStat(StatType.AGILITY)
            }
            StatRow("INT", player.stats.intelligence, player.remainingStatPoints > 0) {
                onIncreaseStat(StatType.INTELLIGENCE)
            }
            StatRow("PER", player.stats.perception, player.remainingStatPoints > 0) {
                onIncreaseStat(StatType.PERCEPTION)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Currency Panel
        HudPanel(isActive = false) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("GOLD", style = shadowTypography.section, color = shadowColors.warning.color)
                Text(
                    "${player.gold} G",
                    style = shadowTypography.statNumber.copy(fontSize = 20.sp),
                    color = colors.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    HabitLevelingTheme {
        val player = Player(
            name = "SUNG JIN-WOO",
            jobClass = "SHADOW MONARCH",
            title = "WOLF SLAYER",
            rank = "E",
            level = 25,
            hp = 850,
            maxHp = 1000,
            mp = 450,
            maxMp = 500,
            remainingStatPoints = 5,
            gold = 12500,
            stats = PlayerStats(
                strength = 80,
                agility = 75,
                intelligence = 40,
                vitality = 90,
                perception = 60
            )
        )
        Column {
            PlayerHeader(player)
            StatusScreen(
                player = player,
                isAtLevelCap = true
            )
        }
    }
}
