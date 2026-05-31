package com.mrifkii.habitleveling.ui.quest.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun QuestCard(
    title: String,
    meta: String,
    rewardXp: Int,
    isCompleted: Boolean,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    tag: String? = null
) {
    val colors = MaterialTheme.colorScheme
    val shadowTypography = LocalShadowTypography.current
    val shadowColors = LocalShadowColors.current

    _root_ide_package_.com.mrifkii.habitleveling.ui.components.HudPanel(
        isActive = !isCompleted,
        tag = tag,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 20dp Custom Checkbox
            QuestCheckbox(
                checked = isCompleted,
                onCheckedChange = { if (!isCompleted) onComplete() },
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (isCompleted) colors.onSurfaceVariant else colors.onSurface,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Text(
                    text = meta,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant
                )
            }

            Text(
                text = "$rewardXp XP",
                style = shadowTypography.statNumber.copy(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                color = if (isCompleted) shadowColors.success.color else colors.primary
            )
        }
    }
}

@Composable
private fun QuestCheckbox(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    
    IconButton(
        onClick = onCheckedChange,
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = if (checked) colors.primary else colors.onSurfaceVariant,
                shape = RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
