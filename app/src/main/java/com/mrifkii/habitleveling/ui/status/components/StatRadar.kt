package com.mrifkii.habitleveling.ui.status.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.domain.model.PlayerStats
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatRadar(stats: PlayerStats) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    val textMeasurer = rememberTextMeasurer()

    val statValues = listOf(
        stats.strength.toFloat(), stats.intelligence.toFloat(), stats.agility.toFloat(),
        stats.vitality.toFloat(), stats.perception.toFloat()
    )

    val labels = listOf("STR", "INT", "AGI", "VIT", "PER")
    val maxStatValue = 100f
    val axisCount = labels.size
    val angleStep = 360.0 / axisCount

    Canvas(modifier = Modifier.size(280.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2 - 50.dp.toPx()

        // 1. Draw 3 concentric rings (Pentagon)
        for (i in 1..3) {
            val ringRadius = radius * (i / 3f)
            val path = Path()
            for (j in 0 until axisCount) {
                val angle = Math.toRadians(j * angleStep - 90.0)
                val x = center.x + ringRadius * cos(angle).toFloat()
                val y = center.y + ringRadius * sin(angle).toFloat()
                if (j == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, colors.outlineVariant, alpha = 0.5f, style = Stroke(1.dp.toPx()))
        }

        // 2. Draw axis lines and labels
        for (j in 0 until axisCount) {
            val angle = Math.toRadians(j * angleStep - 90.0)
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
        for (j in 0 until axisCount) {
            val statVal = statValues.getOrElse(j) { 10f }.coerceIn(0f, maxStatValue)
            val ringRadius = radius * (statVal / maxStatValue)
            val angle = Math.toRadians(j * angleStep - 90.0)
            val x = center.x + ringRadius * cos(angle).toFloat()
            val y = center.y + ringRadius * sin(angle).toFloat()
            if (j == 0) statPath.moveTo(x, y) else statPath.lineTo(x, y)
        }
        statPath.close()
        drawPath(statPath, colors.primary, alpha = 0.2f)
        drawPath(statPath, colors.primary, style = Stroke(1.4.dp.toPx()))

        // 4. Vertex Dots
        for (j in 0 until axisCount) {
            val statVal = statValues.getOrElse(j) { 10f }.coerceIn(0f, maxStatValue)
            val ringRadius = radius * (statVal / maxStatValue)
            val angle = Math.toRadians(j * angleStep - 90.0)
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
