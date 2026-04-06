package com.rabelhautama0097.mytask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.rabelhautama0097.mytask.navigation.SetupNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           val navController = rememberNavController()
            val tasks = remember { mutableStateListOf<String>() }

            SetupNavGraph(
                navController = navController,
                tasks = tasks
            )
        }
    }
}