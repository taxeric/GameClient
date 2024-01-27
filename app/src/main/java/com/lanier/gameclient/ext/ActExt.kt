package com.lanier.gameclient.ext

import androidx.activity.ComponentActivity
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lanier.gameclient.preference.mDefPreferenceStore

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
