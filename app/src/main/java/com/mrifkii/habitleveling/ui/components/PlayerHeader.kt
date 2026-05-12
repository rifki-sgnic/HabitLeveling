package com.mrifkii.habitleveling.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.domain.model.Player
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun PlayerHeader(
    player: Player,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val shadowColors = LocalShadowColors.current
    val shadowTypography = LocalShadowTypography.current

    Surface(
        color = colors.surfaceContainer,
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .drawBehind {
                // Bottom border
                drawLine(
                    color = colors.outlineVariant,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Row 1: LV, RANK, SYSTEM
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "LV. ${player.level}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = colors.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                RankBadge(rank = player.rank)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = player.jobClass.uppercase(),
                    style = shadowTypography.statLabel,
                    color = colors.onSurfaceVariant
                )
            }

            // Row 2: Status Bars Grid [1fr] [60dp] [60dp]
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // XP Bar (1fr)
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "XP",
                            style = shadowTypography.systemTag,
                            color = shadowColors.xp.color
                        )
                        Text(
                            text = "${(player.level * 100)} / ${(player.level + 1) * 100}",
                            style = shadowTypography.timer.copy(fontSize = shadowTypography.systemTag.fontSize),
                            color = colors.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    LinearProgressIndicator(
                        progress = { 0.5f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = shadowColors.xp.color,
                        trackColor = colors.surfaceContainerHigh,
                        strokeCap = StrokeCap.Butt
                    )
                }

                // HP Bar (60dp)
                Column(modifier = Modifier.width(60.dp)) {
                    Text(
                        text = "HP",
                        style = shadowTypography.systemTag,
                        color = shadowColors.hp.color
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    LinearProgressIndicator(
                        progress = { player.hp.toFloat() / player.maxHp },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = shadowColors.hp.color,
                        trackColor = colors.surfaceContainerHigh,
                        strokeCap = StrokeCap.Butt
                    )
                }

                // MP Bar (60dp)
                Column(modifier = Modifier.width(60.dp)) {
                    Text(
                        text = "MP",
                        style = shadowTypography.systemTag,
                        color = shadowColors.mp.color
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    LinearProgressIndicator(
                        progress = { player.mp.toFloat() / player.maxMp },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = shadowColors.mp.color,
                        trackColor = colors.surfaceContainerHigh,
                        strokeCap = StrokeCap.Butt
                    )
                }
            }
        }
    }
}
