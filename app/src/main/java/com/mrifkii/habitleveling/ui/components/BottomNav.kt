package com.mrifkii.habitleveling.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun BottomNav(
    currentTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    
    NavigationBar(
        modifier = Modifier
            .drawBehind {
                // Top border only
                drawLine(
                    color = colors.outlineVariant,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            },
        containerColor = colors.surfaceContainer,
        contentColor = colors.onSurface,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            selected = currentTab == 0,
            onClick = { onTabSelected(0) },
            icon = { 
                Icon(
                    imageVector = Icons.Outlined.Person, 
                    contentDescription = "STATUS",
                    modifier = Modifier.size(24.dp)
                ) 
            },
            label = { 
                Text(
                    text = "STATUS", 
                    style = MaterialTheme.typography.labelMedium
                ) 
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.primary,
                unselectedIconColor = colors.onSurfaceVariant,
                selectedTextColor = colors.primary,
                unselectedTextColor = colors.onSurfaceVariant,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = currentTab == 1,
            onClick = { onTabSelected(1) },
            icon = { 
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.List, 
                    contentDescription = "QUESTS",
                    modifier = Modifier.size(24.dp)
                ) 
            },
            label = { 
                Text(
                    text = "QUESTS",
                    style = MaterialTheme.typography.labelMedium
                ) 
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.primary,
                unselectedIconColor = colors.onSurfaceVariant,
                selectedTextColor = colors.primary,
                unselectedTextColor = colors.onSurfaceVariant,
                indicatorColor = Color.Transparent
            )
        )
    }
}
