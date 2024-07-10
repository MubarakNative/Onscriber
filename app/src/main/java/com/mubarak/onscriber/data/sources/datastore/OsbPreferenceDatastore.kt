package com.mubarak.onscriber.data.sources.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mubarak.onscriber.utils.Constants.NOTE_ITEM_LAYOUT_KEY
import com.mubarak.onscriber.utils.NoteLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OsbPreferenceDatastore @Inject constructor(
    private val datastore: DataStore<Preferences>
) : UserPreference {
    override suspend fun setNoteLayout(noteLayout: String) {
        datastore.edit { mutablePreferences ->
            mutablePreferences[NOTE_LAYOUT_KEY] = noteLayout
        }
    }

    override fun getNoteLayout(): Flow<String> = datastore.data.map { preferences ->
        preferences[NOTE_LAYOUT_KEY] ?: NoteLayout.LIST.name
    }


    companion object {
        val NOTE_LAYOUT_KEY = stringPreferencesKey(NOTE_ITEM_LAYOUT_KEY)
    }

}

interface UserPreference {
    suspend fun setNoteLayout(noteLayout: String)

    fun getNoteLayout(): Flow<String>
}