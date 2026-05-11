package com.rabelhautama0097.mytask.screen

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    var showList by remember { mutableStateOf(true) }

    // DELETE
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    val tasks by viewModel.tasks.collectAsState()

    val context = androidx.compose.ui.platform.LocalContext.current

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
                            showList = !showList
                        }
                    ) {

                        Icon(
                            painter = painterResource(
                                id = if (showList)
                                    R.drawable.outline_grid_view_24
                                else
                                    R.drawable.outline_view_list_24
                            ),
                            contentDescription = stringResource(
                                id = if (showList)
                                    R.string.grid
                                else
                                    R.string.list
                            )
                        )
                    }

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
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text(text = stringResource(R.string.input_hint))
                },
                modifier = Modifier.fillMaxWidth()
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

            if (tasks.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(text = stringResource(R.string.task_list))
                }

            } else {

                if (showList) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 84.dp)
                    ) {

                        items(tasks) { task ->

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        Text(
                                            text = task.title,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = task.priority,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            selectedTask = task
                                            showDialog = true
                                        }
                                    ) {

                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete"
                                        )
                                    }
                                }
                            }

                            HorizontalDivider(
                                thickness = DividerDefaults.Thickness
                            )
                        }
                    }

                } else {

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(tasks) { task ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline
                                )
                            ) {

                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Text(
                                            text = task.title,
                                            modifier = Modifier.weight(1f),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            fontWeight = FontWeight.Bold
                                        )

                                        IconButton(
                                            onClick = {
                                                selectedTask = task
                                                showDialog = true
                                            }
                                        ) {

                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete"
                                            )
                                        }
                                    }

                                    Text(
                                        text = task.priority,
                                        maxLines = 4,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // DIALOG DELETE
        if (showDialog && selectedTask != null) {

            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },

                title = {
                    Text(text = "Konfirmasi")
                },

                text = {
                    Text(text = "Yakin ingin menghapus task ini?")
                },

                confirmButton = {

                    TextButton(
                        onClick = {

                            viewModel.deleteTask(selectedTask!!)

                            showDialog = false
                            selectedTask = null
                        }
                    ) {

                        Text("Hapus")
                    }
                },

                dismissButton = {

                    TextButton(
                        onClick = {

                            showDialog = false
                            selectedTask = null
                        }
                    ) {

                        Text("Batal")
                    }
                }
            )
        }
    }
}