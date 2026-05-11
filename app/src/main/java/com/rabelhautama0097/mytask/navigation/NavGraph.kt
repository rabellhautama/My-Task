package com.rabelhautama0097.mytask.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rabelhautama0097.mytask.screen.AboutScreen
import com.rabelhautama0097.mytask.screen.MainScreen
import com.rabelhautama0097.mytask.screen.TaskViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: TaskViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable("main") {

            MainScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("about") {

            AboutScreen(navController)
        }
    }
}