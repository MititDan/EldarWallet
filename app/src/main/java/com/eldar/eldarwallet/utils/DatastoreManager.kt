package com.eldar.eldarwallet.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

object DatastoreManager {

    val Context.baseDatastore: DataStore<Preferences> by preferencesDataStore(name = "preferences_base")

    suspend inline fun <reified T : Any> saveData(
        context: Context,
        key: Preferences.Key<T>,
        value: T
    ) = withContext(Dispatchers.IO) {
        try {
                context.baseDatastore.edit { preferences ->
                    preferences[key] = value
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch en saveData/DatastoreManager: ${e.message}")
        }
    }

    suspend inline fun <reified T : Any> getData(
        context: Context,
        key: Preferences.Key<T>,
        defaultValue: T
    ): T? = withContext(Dispatchers.IO)  {
        return@withContext try {
            context.baseDatastore.data.map { preferences ->
                preferences[key] ?: defaultValue
            }.first()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch en getData/DatastoreManager: ${e.message}")
            null
        }
    }

    suspend fun clearDatastore(context: Context) = withContext(Dispatchers.IO) {
        try {
            context.baseDatastore.edit { preferences ->
                preferences.clear()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "catch clearDatastore: ${e.message}")
        }
    }
}