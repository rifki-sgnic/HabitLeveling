package com.mrifkii.habitleveling.ui.status

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
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
import com.mrifkii.habitleveling.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun StatusRoute(
    viewModel: StatusViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val player = uiState.player

    when {
        uiState.isLoading -> {
            CircularProgressIndicator()
        }

        player != null -> {
            Column(modifier = Modifier.fillMaxSize()) {
                PlayerHeader(player)
                StatusScreen(player, onIncreaseStat = { viewModel.increaseStat(it) })
            }
        }
    }

}

@Composable
fun StatusScreen(
    player: Player,
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
                onIncreaseStat(
                    StatType.STRENGTH
                )
            }
            StatRow("VIT", player.stats.vitality, player.remainingStatPoints > 0) {
                onIncreaseStat(
                    StatType.VITALITY
                )
            }
            StatRow("AGI", player.stats.agility, player.remainingStatPoints > 0) {
                onIncreaseStat(
                    StatType.AGILITY
                )
            }
            StatRow("INT", player.stats.intelligence, player.remainingStatPoints > 0) {
                onIncreaseStat(
                    StatType.INTELLIGENCE
                )
            }
            StatRow("SEN", player.stats.perception, player.remainingStatPoints > 0) {
                onIncreaseStat(
                    StatType.PERCEPTION
                )
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

@Composable
private fun InfoRow(label: String, value: String, isPrimary: Boolean = false) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = shadowTypography.statLabel, color = colors.onSurfaceVariant)
        Text(
            value.uppercase(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (isPrimary) colors.primary else colors.onSurface
        )
    }
}

@Composable
private fun StatRow(label: String, value: Int, canIncrease: Boolean, onIncrease: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = shadowTypography.section, color = colors.onSurfaceVariant)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                value.toString(),
                style = shadowTypography.statNumber.copy(fontSize = 18.sp),
                color = colors.primary
            )

            if (canIncrease) {
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(
                    onClick = onIncrease,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Increase",
                        tint = colors.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatRadar(stats: PlayerStats) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    val textMeasurer = rememberTextMeasurer()

    val statValues = listOf(
        stats.strength, stats.intelligence, stats.agility,
        stats.perception, stats.vitality, stats.perception // 6 axes
    ).map { it.toFloat() }

    val labels = listOf("STR", "INT", "AGI", "SEN", "VIT", "PER")
    val maxStatValue = 100f

    Canvas(modifier = Modifier.size(280.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2 - 50.dp.toPx()

        // 1. Draw 3 concentric hex rings
        for (i in 1..3) {
            val ringRadius = radius * (i / 3f)
            val path = Path()
            for (j in 0..5) {
                val angle = Math.toRadians(j * 60.0 - 90.0)
                val x = center.x + ringRadius * cos(angle).toFloat()
                val y = center.y + ringRadius * sin(angle).toFloat()
                if (j == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, colors.outlineVariant, alpha = 0.5f, style = Stroke(1.dp.toPx()))
        }

        // 2. Draw 6 axis lines and labels
        for (j in 0..5) {
            val angle = Math.toRadians(j * 60.0 - 90.0)
            val end = Offset(
                center.x + radius * cos(angle).toFloat(),
                center.y + radius * sin(angle).toFloat()
            )
            drawLine(colors.outlineVariant, center, end, strokeWidth = 1.dp.toPx(), alpha = 0.6f)

            // Labels and Values
            val labelRadius = radius + 20.dp.toPx()
            val lx = center.x + labelRadius * cos(angle).toFloat()
            val ly = center.y + labelRadius * sin(angle).toFloat()

            val labelText = labels[j]
            val valueText = statValues[j].toInt().toString()

            val labelLayout = textMeasurer.measure(
                labelText,
                shadowTypography.statLabel.copy(color = colors.onSurfaceVariant)
            )
            val valueLayout = textMeasurer.measure(
                valueText,
                shadowTypography.timer.copy(color = colors.onSurface, fontSize = 12.sp)
            )

            drawText(
                textLayoutResult = labelLayout,
                topLeft = Offset(lx - labelLayout.size.width / 2, ly - labelLayout.size.height)
            )
            drawText(
                textLayoutResult = valueLayout,
                topLeft = Offset(lx - valueLayout.size.width / 2, ly)
            )
        }

        // 3. Draw Stat Polygon
        val statPath = Path()
        for (j in 0..5) {
            val statVal = statValues.getOrElse(j) { 10f }.coerceIn(0f, maxStatValue)
            val ringRadius = radius * (statVal / maxStatValue)
            val angle = Math.toRadians(j * 60.0 - 90.0)
            val x = center.x + ringRadius * cos(angle).toFloat()
            val y = center.y + ringRadius * sin(angle).toFloat()
            if (j == 0) statPath.moveTo(x, y) else statPath.lineTo(x, y)
        }
        statPath.close()
        drawPath(statPath, colors.primary, alpha = 0.2f)
        drawPath(statPath, colors.primary, style = Stroke(1.4.dp.toPx()))

        // 4. Vertex Dots
        for (j in 0..5) {
            val statVal = statValues.getOrElse(j) { 10f }.coerceIn(0f, maxStatValue)
            val ringRadius = radius * (statVal / maxStatValue)
            val angle = Math.toRadians(j * 60.0 - 90.0)
            drawCircle(
                colors.onPrimaryContainer,
                radius = 2.5.dp.toPx(),
                center = Offset(
                    center.x + ringRadius * cos(angle).toFloat(),
                    center.y + ringRadius * sin(angle).toFloat()
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusScreenPreview() {
    HabitLevelingTheme(darkTheme = true) {
        val player = Player(
            name = "SUNG JIN-WOO",
            jobClass = "SHADOW MONARCH",
            title = "WOLF SLAYER",
            rank = "S",
            level = 10,
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
        Column() {
            PlayerHeader(player)
            StatusScreen(
                player
            )
        }
    }
}
