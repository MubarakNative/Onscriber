package com.mubarak.onscriber.ui.addoredit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.OsbDestinationsArgs.NOTE_ID_ARG
import com.mubarak.onscriber.R
import com.mubarak.onscriber.data.sources.OsbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    init {
        Log.i("noteId", noteId.toString())
    }

    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState: StateFlow<AddEditNoteUiState> = _uiState.asStateFlow()

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    init {
        if (noteId != -1L) {
            populateNoteContent(noteId)
        }
    }

    fun saveNote() {
        if (title.isBlank() && content.isBlank()) {
            _uiState.update {
                it.copy(message = R.string.missing_field, navigateToHome = true)
            }
            return
        }

        if (noteId == -1L) {
            createNote()
        } else {
            updateNote()
        }
    }


    private fun createNote() = viewModelScope.launch {
        _uiState.update {
            it.copy(navigateToHome = true)
        }
    }

    private fun updateNote() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                navigateToHome = true
            )
        }
        noteId.let {

        }
    }

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