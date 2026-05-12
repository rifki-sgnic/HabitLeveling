package com.mrifkii.habitleveling.ui.quest.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.ui.components.HudPanel
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun QuestItem(
    quest: Quest,
    onComplete: (Quest) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val shadowColors = LocalShadowColors.current
    val shadowTypography = LocalShadowTypography.current

    HudPanel(
        isActive = !quest.isCompleted,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !quest.isCompleted) { onComplete(quest) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = quest.isCompleted,
                onCheckedChange = { if (!quest.isCompleted) onComplete(quest) },
                colors = CheckboxDefaults.colors(
                    checkedColor = colors.primary,
                    uncheckedColor = colors.onSurfaceVariant,
                    checkmarkColor = colors.onPrimary
                ),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quest.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (quest.isCompleted) colors.onSurfaceVariant else colors.onSurface,
                        textDecoration = if (quest.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Text(
                    text = quest.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant
                )
            }

            Text(
                text = "${quest.rewardXp.toInt()} XP",
                style = shadowTypography.timer,
                color = if (quest.isCompleted) shadowColors.success.color else colors.primary
            )
        }
    }
}
