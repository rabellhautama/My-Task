package com.rabelhautama0097.mytask.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rabelhautama0097.mytask.R
import com.rabelhautama0097.mytask.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {

    var title by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("low") }

    val tasks by viewModel.tasks.collectAsState()

    val context = LocalContext.current

    val errorEmpty = stringResource(R.string.error_empty)
    val highText = stringResource(R.string.high)
    val mediumText = stringResource(R.string.medium)
    val lowText = stringResource(R.string.low)

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.task),
                            contentDescription = "Logo",
                            modifier = Modifier.size(50.dp)
                        )

                        Text(text = stringResource(R.string.title))
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),

                actions = {

                    IconButton(
                        onClick = {

                            val textToShare =
                                "Daftar Tugas:\n" + tasks.joinToString("\n") {
                                    "${it.title} (${it.priority})"
                                }

                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, textToShare)
                            }

                            context.startActivity(
                                Intent.createChooser(intent, "Share ke")
                            )
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate("about")
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About"
                        )
                    }
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text(text = stringResource(R.string.input_hint))
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = stringResource(R.string.priority_label))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = priority == "high",
                    onClick = {
                        priority = "high"
                    }
                )

                Text(text = highText)

                RadioButton(
                    selected = priority == "medium",
                    onClick = {
                        priority = "medium"
                    }
                )

                Text(text = mediumText)

                RadioButton(
                    selected = priority == "low",
                    onClick = {
                        priority = "low"
                    }
                )

                Text(text = lowText)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    if (title.isEmpty()) {

                        error = errorEmpty

                    } else {

                        val priorityText = when (priority) {
                            "high" -> highText
                            "medium" -> mediumText
                            else -> lowText
                        }

                        viewModel.addTask(
                            Task(
                                title = title,
                                priority = priorityText
                            )
                        )

                        title = ""
                        priority = "low"
                        error = ""
                    }
                }
            ) {

                Text(text = stringResource(R.string.add))
            }

            if (error.isNotEmpty()) {

                Text(
                    text = error,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.task_list))

            tasks.forEach { task ->

                Text(
                    text = "- ${task.title} (${task.priority})"
                )
            }
        }
    }
}