package com.rabelhautama0097.mytask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.rabelhautama0097.mytask.database.TaskDb
import com.rabelhautama0097.mytask.navigation.SetupNavGraph
import com.rabelhautama0097.mytask.screen.TaskViewModel
import com.rabelhautama0097.mytask.util.ViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()

            val database = TaskDb.getDatabase(this)
            val dao = database.taskDao()

            val viewModel: TaskViewModel = viewModel(
                factory = ViewModelFactory(dao)
            )

            SetupNavGraph(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}