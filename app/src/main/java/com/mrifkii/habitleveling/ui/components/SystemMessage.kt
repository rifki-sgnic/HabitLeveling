package com.mrifkii.habitleveling.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun SystemMessage(
    message: String,
    modifier: Modifier = Modifier,
    tag: String = "SHADOW GUIDE"
) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current

    HudPanel(
        isActive = true,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 44dp Circular Avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(colors.secondary, colors.background)
                        )
                    )
                    .border(1.5.dp, colors.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder for Avatar Icon
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "[ $tag ]",
                    style = shadowTypography.systemTag,
                    color = colors.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurface
                )
            }
        }
    }
}
