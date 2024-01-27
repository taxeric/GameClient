package com.lanier.gameclient.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

/**
 * Created by 幻弦让叶
 * Date 2024/1/27 20:23
 */

private const val DEFAULT_PREFERENCE_FILENAME = ""

val Context.mDefPreferenceStore: DataStore<Preferences> by preferencesDataStore(name = DEFAULT_PREFERENCE_FILENAME)
