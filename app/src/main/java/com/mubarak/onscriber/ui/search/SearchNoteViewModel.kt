package com.mubarak.onscriber.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.data.sources.OsbRepository
import com.mubarak.onscriber.data.sources.local.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchNoteUiState(
    val notes: List<Note> = emptyList()
)

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val osbRepository: OsbRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchNoteUiState())
        private set

    var searchQuery: String by mutableStateOf("")
        private set

    fun searchNote(searchQuery: String) {
        val queryFilter = SearchQueryFilter.filterQuery(searchQuery)
        this.searchQuery = searchQuery
        viewModelScope.launch {
            osbRepository.getNoteBySearch(queryFilter).collect { notes ->
                uiState = uiState.copy(
                    notes = notes
                )
            }
        }
    }
}
