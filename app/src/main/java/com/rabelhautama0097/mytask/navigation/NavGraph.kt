package com.rabelhautama0097.mytask.navigation

import AboutScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rabelhautama0097.mytask.screen.MainScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    tasks: MutableList<String>
) {

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {
            MainScreen(
                navController = navController,
                tasks = tasks,
                onAddTask = { newTask ->
                    tasks.add(newTask)
                }
            )
        }

        composable("about") {
            AboutScreen(navController = navController)
        }
    }
}