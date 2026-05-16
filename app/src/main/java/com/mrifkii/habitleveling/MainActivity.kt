package com.mrifkii.habitleveling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.mrifkii.habitleveling.navigation.AppNavHost
import com.mrifkii.habitleveling.ui.theme.HabitLevelingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d("MainActivity", "onCreate called")
        viewModel.launchWorker()
        enableEdgeToEdge()
        setContent {
            HabitLevelingTheme {
                AppNavHost()
            }
        }
    }
}


