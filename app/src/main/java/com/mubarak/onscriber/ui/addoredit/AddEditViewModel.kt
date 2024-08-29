package com.mubarak.onscriber.ui.addoredit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.ui.OsbDestinationsArgs.NOTE_ID_ARG
import com.mubarak.onscriber.R
import com.mubarak.onscriber.data.sources.OsbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddEditNoteUiState(
    val message: Int? = null,
    val isNoteDeleted: Boolean = false,
    val navigateToHome: Boolean = false
)

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val osbRepository: OsbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Long = checkNotNull(savedStateHandle[NOTE_ID_ARG])

    private val isNewNote: Boolean = noteId == -1L

    var uiState by mutableStateOf(AddEditNoteUiState())
        private set

    var title by mutableStateOf("") // UI element state also can be placed in a screen level state if that required business logic
        private set

    var content by mutableStateOf("")
        private set

    init {
        if (!isNewNote) {
            populateNoteContent(noteId)
        }
    }

    fun saveNote() {
        if (title.isBlank() && content.isBlank()) {
            uiState = uiState.copy(
                message = R.string.missing_field
            )
            return
        }

        if (noteId == -1L) {
            createNote()
        } else {
            updateNote()
        }
    }


    private fun createNote() = viewModelScope.launch {
        uiState = uiState.copy(
            navigateToHome = true
        )
        osbRepository.insertNote(title, content)
    }

    private fun updateNote() = viewModelScope.launch {
        uiState = uiState.copy(
            navigateToHome = true
        )
        osbRepository.upsertNote(noteId, title, content)
    }

    fun deleteNote() = viewModelScope.launch {
        if (!isNewNote){
            osbRepository.deleteNoteById(noteId)
            uiState = uiState.copy(
                navigateToHome = true
            )
        }
    }ed

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(description: String) {
        this.content = description
    }

    private fun populateNoteContent(noteId: Long) {
        viewModelScope.launch {
            osbRepository.getNoteById(
                noteId
            ).let {
                title = it.title
                content = it.content
            }
        }
    }
}