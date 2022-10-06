package com.dzakyhdr.authentication

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserDataStoreManager(private val context: Context) {

    suspend fun saveUser(status: Boolean, id: Int) {
        context.userDataStore.edit { perf ->
            perf[STATUS_KEY] = status
            perf[ID_KEY] = id
        }
    }

    suspend fun logOutUser() {
        context.userDataStore.edit { perf ->
            perf.remove(STATUS_KEY)
            perf.remove(ID_KEY)
        }
    }

    fun getStatus(): Flow<Boolean> {
        return context.userDataStore.data.map { pref ->
            pref[STATUS_KEY] ?: false
        }
    }

    fun getId(): Flow<Int> {
        return context.userDataStore.data.map { pref ->
            pref[ID_KEY] ?: 0
        }
    }

    companion object {
        private const val DATASTORE_NAME = "user_preference"
        private val ID_KEY = intPreferencesKey("id_key")
        private val STATUS_KEY = booleanPreferencesKey("status_key")

        private val Context.userDataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}