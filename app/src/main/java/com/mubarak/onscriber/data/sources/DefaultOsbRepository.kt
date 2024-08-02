package com.mubarak.onscriber.data.sources

import com.mubarak.onscriber.data.sources.local.OsbDatabase
import com.mubarak.onscriber.data.sources.local.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class DefaultOsbRepository @Inject constructor(
    private val osbDatabase: OsbDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
):OsbRepository {

    override suspend fun insertNote(title: String, content: String)= withContext(dispatcher) {
        val id = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE // generate Random Positive LONG value
        val note = Note(
            id = id,
            title = title,
            content = content
        )
        osbDatabase.getDao().insertNote(
            note
        )
    }

    override suspend fun upsertNote(noteId: Long, title: String, content: String) {
       val updateNote = getNoteById(noteId).copy(
           id = noteId,
           title = title,
           content = content
       )
       osbDatabase.getDao().upsertNote(
           updateNote
       )
    }

    override fun getAllNote(): Flow<List<Note>> {
        return osbDatabase.getDao().getAllNotes()
    }

    override fun getNoteBySearch(searchQuery: String): Flow<List<Note>> {
        return osbDatabase.getDao().getSearchedNote(searchQuery)
    }

    override suspend fun deleteNote(noteId: Long) {
        val note = getNoteById(noteId)
        osbDatabase.getDao().deleteNote(note)
    }

    override suspend fun deleteNoteById(noteId: Long) {
        osbDatabase.getDao().deleteNoteById(noteId)
    }

    override suspend fun deleteAllNotes() {
        osbDatabase.getDao().deleteAllNotes()
    }

    override fun getNoteStreamById(noteId: Long): Flow<Note> {
        return osbDatabase.getDao().getNoteStream(noteId)
    }

    override suspend fun getNoteById(noteId: Long): Note {
        return withContext(dispatcher){
            osbDatabase.getDao().getNoteById(noteId)
        }
    }
}