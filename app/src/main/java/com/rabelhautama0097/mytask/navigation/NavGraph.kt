package com.rabelhautama0097.mytask.navigation

import AboutScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rabelhautama0097.mytask.screen.DetailScreen
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
        composable(
            route = "detail/{title}/{priority}",

            arguments = listOf(
                navArgument("title") {
                    type = NavType.StringType
                },

                navArgument("priority") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val title =
                backStackEntry.arguments?.getString("title") ?: ""

            val priority =
                backStackEntry.arguments?.getString("priority") ?: ""

            DetailScreen(
                title = title,
                priority = priority
            )
        }
    }
}