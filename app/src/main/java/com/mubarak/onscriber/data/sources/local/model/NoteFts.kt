package com.mubarak.onscriber.data.sources.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Entity(tableName = "note_fts")
@Fts4(contentEntity = Note::class)
data class NoteFts(

    @ColumnInfo("rowid")
    @PrimaryKey
    val id:Long,

    @ColumnInfo("Title")
    val title:String,

    @ColumnInfo("Content")
    val content:String
)
