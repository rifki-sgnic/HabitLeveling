package com.mrifkii.habitleveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mrifkii.habitleveling.navigation.AppNavHost
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()
        setContent {
            HabitLevelingTheme {
                AppNavHost()
            }
        }
    }
}


