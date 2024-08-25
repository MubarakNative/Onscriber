package com.mubarak.onscriber.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mubarak.onscriber.R
import com.mubarak.onscriber.data.sources.local.model.Note
import com.mubarak.onscriber.ui.note.OsbNoteItemLists
import com.mubarak.onscriber.ui.theme.OnscriberTheme

@Composable
fun OsbSearchScreen(
    modifier: Modifier = Modifier,
    onUpButtonClick: () -> Unit = {},
    viewModel: SearchNoteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OsbSearchScreen(
        modifier = modifier,
        onUpButtonClick = onUpButtonClick,
        searchQuery = viewModel.searchQuery,
        notes = uiState.notes,
        queryChangeValue = {viewModel.searchNote(it)}
    )
}

@Composable
fun OsbSearchScreen(
    modifier: Modifier = Modifier,
    onUpButtonClick: () -> Unit,
    searchQuery:String,
    notes:List<Note>,
    queryChangeValue: (String) -> Unit
){
    OnscriberTheme {
        Scaffold(modifier = modifier, topBar = {
            SearchNoteTopAppBar(
                modifier = modifier,
                query = searchQuery,
                onUpButtonClick = {
                    onUpButtonClick()
                },
                onValueChange = queryChangeValue)
        }) {
            OsbNoteItemLists(
                modifier = Modifier.padding(it),
                notes = notes,
            )
        }
    }

}

@Composable
fun SearchNoteTopAppBar(
    modifier: Modifier = Modifier,
    query: String,
    onValueChange: (String) -> Unit = {},
    onUpButtonClick: () -> Unit = {}
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(16.dp),
        value = query,
        onValueChange = {
            onValueChange(it)
        },
        leadingIcon = {
            IconButton(onClick = {
                onUpButtonClick()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }

        },
        placeholder = {
            Text(
                text = stringResource(R.string.searchNote)
            )
        }
    )
}

@Preview
@Composable
private fun OsbSearchScreenPreview() {
    OnscriberTheme {
        OsbSearchScreen(
            modifier = Modifier,
            onUpButtonClick = {},
            searchQuery = "compose",
            queryChangeValue = {},
            notes = emptyList()
        )
    }
}