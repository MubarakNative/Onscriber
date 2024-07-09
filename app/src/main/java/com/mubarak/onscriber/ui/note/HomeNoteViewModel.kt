package com.mubarak.onscriber.ui.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.data.sources.OsbRepository
import com.mubarak.onscriber.data.sources.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeNoteUiState(
    val notes: List<Note> = emptyList(),
    val message: Int? = null
    // TODO: later we add more features like sorting, filtering etc.
)

@HiltViewModel
class HomeNoteViewModel @Inject constructor(
    private val osbRepository: OsbRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeNoteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            osbRepository.getAllNote().collect {
                _uiState.value = HomeNoteUiState(it)
            }
        }
    }
}