package com.rabelhautama0097.mytask

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {

    var title by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        Text("My To-Do List")

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Masukkan tugas") }
        )

        Button(onClick = {
            if (title.isNotEmpty()) {
                tasks = tasks + title
                title = ""
            }
        }) {
            Text("Tambah")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Daftar Tugas:")

        tasks.forEach {
            Text("- $it")
        }
    }
}
