package com.mrifkii.habitleveling.ui.status.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun InfoRow(label: String, value: String, isPrimary: Boolean = false) {
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
