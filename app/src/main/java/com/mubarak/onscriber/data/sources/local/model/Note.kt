package com.mubarak.onscriber.data.sources.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("note")
data class Note(
    @PrimaryKey
    @ColumnInfo("note_id")
    var id: Long,

    @ColumnInfo("Title")
    val title: String,

    @ColumnInfo("Content")
    val content: String
)
