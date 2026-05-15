package com.mrifkii.habitleveling.ui.quest.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.domain.model.Quest
import com.mrifkii.habitleveling.domain.model.QuestType
import com.mrifkii.habitleveling.ui.components.HudPanel
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun QuestCategoryList(
    quests: List<Quest>,
    onTypeSelected: (QuestType) -> Unit
) {
    val shadowColors = LocalShadowColors.current
    val shadowTypography = LocalShadowTypography.current
    val colors = MaterialTheme.colorScheme

    val availableTypes = quests.map { it.type }.distinct().sortedBy { it.ordinal }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(availableTypes) { index, type ->
            val questCount = quests.count { it.type == type }
            val completedCount = quests.count { it.type == type && it.isCompleted }
            
            HudPanel(
                tag = "TYPE ${index + 1}",
                isActive = type == QuestType.RANK_UP,
                modifier = Modifier.clickable { onTypeSelected(type) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = type.name.replace("_", " "),
                            style = shadowTypography.section,
                            color = if (type == QuestType.RANK_UP) shadowColors.warning.color else colors.primary
                        )
                        Text(
                            text = "$completedCount / $questCount CLEARED",
                            style = shadowTypography.statLabel,
                            color = colors.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
