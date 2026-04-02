package com.rabelhautama0097.mytask

import AboutScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           val navController = rememberNavController()
            var tasks by remember { mutableStateOf(listOf<String>()) }

            NavHost(navController = navController, startDestination = "main") {
                composable("main") {
                    MainScreen(navController = navController,tasks = tasks,
                        onAddTask = { newTask -> tasks = tasks + newTask} )
                }
                composable("About") {
                    AboutScreen(navController)
                }
            }
        }
    }
}