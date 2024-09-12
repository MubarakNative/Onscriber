package com.mubarak.onscriber.ui

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class AddEdit(val noteId: Long,val title: Int)

@Serializable
object Settings

@Serializable
object Search