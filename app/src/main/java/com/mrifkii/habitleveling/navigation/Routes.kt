package com.mrifkii.habitleveling.navigation

sealed class Routes(val route: String) {
    data object Status : Routes("status")
    data object Quest : Routes("quest")
}