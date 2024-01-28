package com.lanier.gameclient.ext

import androidx.activity.ComponentActivity
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lanier.gameclient.preference.getPreferencesKey
import com.lanier.gameclient.preference.mDefPreferenceStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:51
 */
fun ComponentActivity.saveToPreferences(
    key: String,
    value: String,
) {
    launchSafe {
        mDefPreferenceStore.edit { settings ->
            settings[stringPreferencesKey(key)] = value
        }
    }
}

fun ComponentActivity.saveToPreferences(
    key: String,
    value: Boolean,
) {
    launchSafe {
        mDefPreferenceStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }
}

fun ComponentActivity.saveToPreferences(
    action: ((MutablePreferences) -> Unit)? = null
) {
    launchSafe {
        mDefPreferenceStore.edit { settings ->
            action?.invoke(settings)
        }
    }
}

fun ComponentActivity.saveToPreferences(
    key: String,
    value: Int,
) {
    launchSafe {
        mDefPreferenceStore.edit { settings ->
            settings[intPreferencesKey(key)] = value
        }
    }
}

fun <T> ComponentActivity.saveToPreferences(
    key: Preferences.Key<T>,
    value: T,
) {
    launchSafe {
        mDefPreferenceStore.edit { settings ->
            settings[key] = value
        }
    }
}

fun ComponentActivity.listenAllPreferences(
    action: ((Map<Preferences.Key<*>, Any>) -> Unit)? = null
) {
    launchSafe {
        mDefPreferenceStore.data
            .catch {
                emit(emptyPreferences())
            }
            .map {
                it.asMap()
            }
            .collect {
                action?.invoke(it)
            }
    }
}

fun ComponentActivity.readStringFromPreferences(
    key: String,
    def: String = "",
) = readFromPreferences(key) ?: def

fun ComponentActivity.readIntFromPreferences(
    key: String,
    def: Int = 0,
) = readFromPreferences(key) ?: def

fun ComponentActivity.readBooleanFromPreferences(
    key: String,
    def: Boolean = false,
) = readFromPreferences(key) ?: def

/**
 * 同步获取数据, 可能阻塞
 */
inline fun <reified T : Any> ComponentActivity.readFromPreferences(
    key: String
) = runBlocking {
    mDefPreferenceStore.data.map { getPreferencesKey<T>(key) }.first() as? T
}
