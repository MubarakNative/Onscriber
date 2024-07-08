package com.mubarak.onscriber.ui.note

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.data.sources.OsbRepository
import com.mubarak.onscriber.data.sources.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeNoteUiState(
    val notes: List<Note> = emptyList(),
    val message: Int? = null,
    val searchResults: List<Note> = emptyList()
    // TODO: later we add more features like sorting, filtering etc.
)

@HiltViewModel
class HomeNoteViewModel @Inject constructor(
    private val osbRepository: OsbRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

   // private val message: Int = checkNotNull(savedStateHandle["message"])

    var searchQuery by mutableStateOf("")
        private set
    

    private val _uiState = MutableStateFlow(HomeNoteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            osbRepository.getAllNote().collect {
                _uiState.value = HomeNoteUiState(it)
            }
            osbRepository.getNoteBySearch(searchQuery).collect{ results->
                _uiState.update { it.copy(searchResults = results) }
            }
         /*   _uiState.update {
                it.copy(message = message)
            }*/
        }
    }
}