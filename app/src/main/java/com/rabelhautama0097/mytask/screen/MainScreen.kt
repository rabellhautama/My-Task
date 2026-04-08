package com.rabelhautama0097.mytask.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    tasks: List<String>,
    onAddTask: (String) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val context = LocalContext.current
    var priority by remember { mutableStateOf("low")}


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
                        Text(stringResource(R.string.title))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                actions = {
                    IconButton(onClick = {
                        val textToShare = "Daftar Tugas:\n" + tasks.joinToString ("\n")
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, textToShare)
                        }
                        context.startActivity(Intent.createChooser(intent, "share ke"))

                    }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                    IconButton(onClick = {
                        navController.navigate("About")
                    }) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                        Spacer(modifier = Modifier.width(8.dp))
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
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.input_hint))}
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(stringResource(R.string.priority_label))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = priority == "high",
                    onClick = { priority = "high"}
                )
                Text(stringResource(R.string.high))

                RadioButton(
                    selected = priority == "medium",
                    onClick = { priority = "medium"}
                )
                Text(stringResource(R.string.medium))

                RadioButton(
                    selected = priority == "low",
                    onClick = { priority = "low"}
                )
                Text(stringResource(R.string.low))
            }

            Button(onClick = {
                if (title.isEmpty()) {
                    error = context.getString(R.string.error_empty)
                } else {
                    val priorityText = when (priority) {
                        "high" -> context.getString(R.string.high)
                        "medium" -> context.getString(R.string.medium)
                        else -> context.getString(R.string.low)
                    }
                    onAddTask("$title ($priorityText)")
                    title = ""
                    error = ""
                }

            }) {
                Text(stringResource(R.string.add))
            }
            if (error.isNotEmpty()) {
                Text(error, color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))

                Text(stringResource(R.string.task_list))

            tasks.forEach {
                Text("- $it")
            }
        }
    }
}