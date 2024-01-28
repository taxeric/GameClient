package com.lanier.gameclient.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:23
 */

private const val DEFAULT_PREFERENCE_FILENAME = "def_preferences"

val Context.mDefPreferenceStore: DataStore<Preferences> by preferencesDataStore(name = DEFAULT_PREFERENCE_FILENAME)

fun getStringKey(key: String) = stringPreferencesKey(key)
fun getIntKey(key: String) = intPreferencesKey(key)
fun getBooleanKey(key: String) = booleanPreferencesKey(key)

inline fun <reified T : Any> getPreferencesKey(
    key: String
) = when (T::class) {
    Int::class -> {
        intPreferencesKey(key)
    }
    String::class -> {
        stringPreferencesKey(key)
    }
    Boolean::class -> {
        booleanPreferencesKey(key)
    }
    else -> {
        throw IllegalArgumentException("err")
    }
}
