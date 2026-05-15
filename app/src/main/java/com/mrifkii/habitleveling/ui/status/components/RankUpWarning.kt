package com.mrifkii.habitleveling.ui.status.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun RankUpWarning(isAtCap: Boolean, rank: String) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    val shadowColors = LocalShadowColors.current

    if (isAtCap) {
        Surface(
            color = shadowColors.warning.color.copy(alpha = 0.1f),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = shadowColors.warning.color,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = strokeWidth
                    )
                    drawLine(
                        color = shadowColors.warning.color,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = shadowColors.warning.color
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "[RANK UP QUEST REQUIRED]",
                        style = shadowTypography.section,
                        color = shadowColors.warning.color
                    )
                    Text(
                        text = "You have reached the level cap for Rank $rank. Complete a 'Job Change' quest to Arise.",
                        style = shadowTypography.statLabel,
                        color = colors.onSurface
                    )
                }
            }
        }
    }
}
