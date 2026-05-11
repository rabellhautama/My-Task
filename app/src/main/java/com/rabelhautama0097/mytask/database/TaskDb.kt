package com.rabelhautama0097.mytask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rabelhautama0097.mytask.model.Task

@Database(
    entities = [Task::class],
    version = 1
)
abstract class TaskDb : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        @Volatile
        private var INSTANCE: TaskDb? = null

        fun getDatabase(context: Context): TaskDb {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDb::class.java,
                    "task_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}