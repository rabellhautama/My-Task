package com.rabelhautama0097.mytask

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {

    var title by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<String>()) }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My To-Do List") },
                actions = {
                    IconButton(onClick = {
                        error = "Fitur Share belum jadi, baru mau Proses"
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
                label = { Text("Masukkan tugas") }
            )

            Button(onClick = {
                if (title.isEmpty()) {
                    error = "Judul Tidak Boleh Kosong"
                } else {
                    tasks = tasks + title
                    title = ""
                    error = ""
                }
            }) {
                Text("Tambah")
            }
            if (error.isNotEmpty()) {
                Text(error, color = androidx.compose.ui.graphics.Color.Red)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Daftar Tugas:")

            tasks.forEach {
                Text("- $it")
            }
        }
    }
}