package com.mrifkii.habitleveling.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun HudPanel(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    tag: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    
    val borderColor = if (isActive) colors.primary else colors.outlineVariant
    val containerColor = colors.surfaceContainer.copy(alpha = 0.85f)
    
    // Glitch animation state
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    
    val scanlineY = rememberInfiniteTransition(label = "scanline").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scanlineY"
    )

    Box(modifier = modifier.padding(top = 10.dp, start = 2.dp, end = 2.dp, bottom = 2.dp)) { // Outer padding for brackets
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    // Decorative Brackets (2dp outside the border)
                    val bracketSize = 12.dp.toPx()
                    val strokeWidth = 1.5.dp.toPx()
                    val offset = 2.dp.toPx()
                    val bracketColor = colors.primary

                    // Top Left
                    drawLine(bracketColor, Offset(-offset, -offset), Offset(bracketSize - offset, -offset), strokeWidth)
                    drawLine(bracketColor, Offset(-offset, -offset), Offset(-offset, bracketSize - offset), strokeWidth)

                    // Top Right
                    drawLine(bracketColor, Offset(size.width + offset, -offset), Offset(size.width - bracketSize + offset, -offset), strokeWidth)
                    drawLine(bracketColor, Offset(size.width + offset, -offset), Offset(size.width + offset, bracketSize - offset), strokeWidth)

                    // Bottom Left
                    drawLine(bracketColor, Offset(-offset, size.height + offset), Offset(bracketSize - offset, size.height + offset), strokeWidth)
                    drawLine(bracketColor, Offset(-offset, size.height + offset), Offset(-offset, size.height - bracketSize + offset), strokeWidth)

                    // Bottom Right
                    drawLine(bracketColor, Offset(size.width + offset, size.height + offset), Offset(size.width - bracketSize + offset, size.height + offset), strokeWidth)
                    drawLine(bracketColor, Offset(size.width + offset, size.height + offset), Offset(size.width + offset, size.height - bracketSize + offset), strokeWidth)
                    
                    // Scanline sweep
                    if (visible) {
                        val currentY = size.height * scanlineY.value
                        drawLine(
                            color = colors.primary.copy(alpha = 0.15f),
                            start = Offset(0f, currentY),
                            end = Offset(size.width, currentY),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                },
            colors = CardDefaults.outlinedCardColors(
                containerColor = containerColor,
                contentColor = colors.onSurface
            ),
            border = CardDefaults.outlinedCardBorder(enabled = true).copy(
                brush = SolidColor(borderColor)
            ),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                content = content
            )
        }

        if (tag != null) {
            Surface(
                color = colors.background,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = (-10).dp)
                    .padding(horizontal = 6.dp)
            ) {
                Text(
                    text = "[ $tag ]",
                    style = shadowTypography.systemTag,
                    color = colors.primary
                )
            }
        }
    }
}
