package com.mubarak.onscriber.data.sources

import com.mubarak.onscriber.data.sources.local.model.Note
import kotlinx.coroutines.flow.Flow

interface OsbRepository {

    suspend fun insertNote(title: String, content: String)

    suspend fun upsertNote(noteId: Long, title: String, content: String)

    fun getAllNote(): Flow<List<Note>>

    fun getNoteBySearch(searchQuery: String): Flow<List<Note>>

    suspend fun deleteNote(noteId: Long)

    suspend fun deleteNoteById(noteId: Long)

    suspend fun deleteAllNotes()

    fun getNoteStreamById(noteId: Long): Flow<Note>

    suspend fun getNoteById(noteId: Long): Note
}