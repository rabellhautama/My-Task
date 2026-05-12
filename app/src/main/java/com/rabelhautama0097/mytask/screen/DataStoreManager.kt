package com.rabelhautama0097.mytask.screen

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val SHOW_LIST_KEY = booleanPreferencesKey("show_list")
    }

    val showListFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SHOW_LIST_KEY] ?: true
    }

    suspend fun saveShowList(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_LIST_KEY] = isList
        }
    }
}