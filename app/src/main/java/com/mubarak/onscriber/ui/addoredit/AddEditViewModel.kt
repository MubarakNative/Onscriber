package com.mubarak.onscriber.ui.addoredit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.R
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
class AddEditViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle
) :ViewModel(){


    private val noteId: Long? = savedStateHandle["noteId"]

    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState: StateFlow<AddEditNoteUiState> = _uiState.asStateFlow()

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    init {
        noteId?.let { id ->
            populateNoteContent(id)
        }
    }

    fun saveNote() {
        if (title.isBlank() && content.isBlank()) {
            _uiState.update {
                it.copy(message = R.string.missing_field, navigateToHome = true)
            }
            return
        }

        if (noteId == null) {
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
        noteId?.let {

        }
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(description: String) {
        this.content = description
    }

    private fun populateNoteContent(noteId:Long){

    }
}