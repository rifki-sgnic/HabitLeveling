package com.mrifkii.habitleveling.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun StatBar(
    icon: ImageVector?,
    label: String,
    current: Int,
    max: Int,
    color: Color,
    modifier: Modifier = Modifier,
    delta: Int? = null
) {
    val shadowTypography = LocalShadowTypography.current
    val shadowColors = LocalShadowColors.current
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // [14dp icon]
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = colors.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        // [56dp label]
        Text(
            text = label.uppercase(),
            style = shadowTypography.statLabel,
            modifier = Modifier.width(56.dp),
            color = colors.onSurfaceVariant
        )

        // [1fr bar]
        LinearProgressIndicator(
            progress = { (current.toFloat() / max.toFloat()).coerceIn(0f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(4.dp),
            color = color,
            trackColor = colors.surfaceContainerHigh.copy(alpha = 0.4f),
            strokeCap = StrokeCap.Round
        )

        // [50dp number]
        Text(
            text = "$current",
            style = shadowTypography.timer, // Use timer for mono numbers
            modifier = Modifier
                .width(50.dp)
                .padding(start = 8.dp),
            color = colors.onSurface,
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )

        // [36dp delta]
        if (delta != null) {
            val deltaText = if (delta >= 0) "+$delta" else "$delta"
            val deltaColor = if (delta >= 0) shadowColors.success.color else colors.error
            Text(
                text = deltaText,
                style = shadowTypography.systemTag.copy(fontSize = 10.sp),
                modifier = Modifier.width(36.dp),
                color = deltaColor,
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        } else {
            Spacer(modifier = Modifier.width(36.dp))
        }
    }
}
