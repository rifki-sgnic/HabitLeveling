package com.mrifkii.habitleveling.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.SystemBlue

@Composable
fun SystemWindow(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp),
                ambientColor = SystemBlue,
                spotColor = SystemBlue
            )
            .clip(RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A0B10).copy(alpha = 0.85f),
                        Color(0xFF00151A).copy(alpha = 0.95f)
                    )
                )
            )
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(SystemBlue, SystemBlue.copy(alpha = 0.3f), SystemBlue)
                ),
                shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(2.dp)
            .border(
                width = 1.dp,
                color = SystemBlue.copy(alpha = 0.2f),
                shape = RoundedCornerShape(topStart = 22.dp, bottomEnd = 22.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            content = content
        )
    }
}
