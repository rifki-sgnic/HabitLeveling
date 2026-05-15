package com.mrifkii.habitleveling.ui.status.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun StatRow(label: String, value: Int, canIncrease: Boolean, onIncrease: () -> Unit) {
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
