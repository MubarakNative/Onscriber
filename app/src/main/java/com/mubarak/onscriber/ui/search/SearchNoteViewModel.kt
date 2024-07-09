package com.mubarak.onscriber.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.data.sources.OsbRepository
import com.mubarak.onscriber.data.sources.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchNoteUiState(
    val notes: List<Note> = emptyList()
)

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val osbRepository: OsbRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchNoteUiState())
    val uiState: StateFlow<SearchNoteUiState> = _uiState.asStateFlow()

    var searchQuery: String by mutableStateOf("")
        private set

    fun searchNote(searchQuery: String) {
        val queryFilter = SearchQueryFilter.filterQuery(searchQuery)
        this.searchQuery = searchQuery
        viewModelScope.launch {
            osbRepository.getNoteBySearch(queryFilter).collect { notes ->
                _uiState.update {
                    it.copy(
                        notes = notes
                    )
                }
            }
        }
    }
}
