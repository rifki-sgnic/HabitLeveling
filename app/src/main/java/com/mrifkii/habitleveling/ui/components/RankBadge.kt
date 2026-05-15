package com.mrifkii.habitleveling.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrifkii.habitleveling.ui.theme.LocalShadowColors
import com.mrifkii.habitleveling.ui.theme.LocalShadowTypography

@Composable
fun RankBadge(
    rank: String,
    modifier: Modifier = Modifier
) {
    val shadowColors = LocalShadowColors.current
    val shadowTypography = LocalShadowTypography.current
    
    val rankRole = when (rank.uppercase()) {
        "E" -> shadowColors.rankE
        "D" -> shadowColors.rankD
        "C" -> shadowColors.rankC
        "B" -> shadowColors.rankB
        "A" -> shadowColors.rankA
        "S" -> shadowColors.rankS
        "NATIONAL" -> shadowColors.rankNational
        else -> shadowColors.rankE
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = rank.uppercase(),
                style = shadowTypography.section
            )
        },
        modifier = modifier,
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = rankRole.color,
            borderWidth = 1.5.dp
        ),
        colors = AssistChipDefaults.assistChipColors(
            labelColor = rankRole.color,
            containerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraSmall
    )
}
