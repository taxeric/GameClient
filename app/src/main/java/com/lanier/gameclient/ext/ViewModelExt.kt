package com.lanier.gameclient.ext

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * Created by 幻弦让叶
 * Date 2024/1/28 16:27
 */
fun ViewModel.readStringFromPreferences(
    dataStore: DataStore<Preferences>,
    key: String,
    def: String = "",
) {
    launchSafe {
        dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[stringPreferencesKey(key)] ?: def
            }
            .asLiveData()
    }
}

fun ViewModel.readIntFromPreferences(
    dataStore: DataStore<Preferences>,
    key: String,
    def: Int = 0,
) {
    launchSafe {
        dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[intPreferencesKey(key)] ?: def
            }
            .asLiveData()
    }
}

fun ViewModel.readBooleanFromPreferences(
    dataStore: DataStore<Preferences>,
    key: String,
    def: Boolean = false,
) {
    launchSafe {
        dataStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it[booleanPreferencesKey(key)] ?: def
            }
            .asLiveData()
    }
}
