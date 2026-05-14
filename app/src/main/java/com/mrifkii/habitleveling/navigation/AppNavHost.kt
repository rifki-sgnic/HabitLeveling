package com.mrifkii.habitleveling.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mrifkii.habitleveling.ui.components.BottomNav
import com.mrifkii.habitleveling.ui.quest.QuestRoute
import com.mrifkii.habitleveling.ui.status.StatusRoute

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNav(
                currentRoute = currentRoute,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.Status.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.Status.route) {
                StatusRoute()
            }

            composable(Routes.Quest.route) {
                QuestRoute()
            }
        }
    }
}