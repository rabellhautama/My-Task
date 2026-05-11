package com.rabelhautama0097.mytask.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rabelhautama0097.mytask.database.TaskDao
import com.rabelhautama0097.mytask.screen.TaskViewModel

class ViewModelFactory(
    private val dao: TaskDao
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}