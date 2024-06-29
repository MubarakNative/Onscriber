package com.mubarak.onscriber.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mubarak.onscriber.data.sources.local.dao.NoteDao
import com.mubarak.onscriber.data.sources.local.model.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class OsbDatabase:RoomDatabase() {
    abstract fun getDao(): NoteDao
}