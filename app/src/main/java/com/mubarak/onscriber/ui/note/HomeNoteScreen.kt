package com.mubarak.onscriber.ui.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mubarak.onscriber.R
import com.mubarak.onscriber.data.sources.local.model.Note
import com.mubarak.onscriber.ui.theme.OnscriberTheme

@Composable
fun OsbHomeScreen(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onFabClick: () -> Unit,
    onSearchActionClick: () -> Unit = {},
    onItemClick: (Note) -> Unit = {},
    viewModel: HomeNoteViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {
                        //TODO: navigate to Drawing Screen
                    }) {
                        Icon(
                            Icons.Outlined.Draw,
                            contentDescription = stringResource(id = R.string.drawNote)
                        )
                    }
                    IconButton(onClick = {
                        //TODO: navigate to Speech to text screen
                    }) {
                        Icon(
                            Icons.Outlined.Mic,
                            contentDescription = stringResource(id = R.string.speechNote),
                        )
                    }
                    IconButton(onClick = {
                        // TODO: navigate to image note screen
                    }) {
                        Icon(
                            Icons.Outlined.Image,
                            contentDescription = stringResource(id = R.string.imageNote)
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = onFabClick,
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, stringResource(id = R.string.create_note))
                    }
                },
                containerColor = Color.Transparent
            )
        },
        topBar = {
            OsbTopAppBar(onMenuClick = {
                onDrawerClick()
            }, searchActionClick = onSearchActionClick, modifier = modifier)
        }
    ) {

        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        OsbNoteItemLists(
            modifier = Modifier.padding(it),
            notes = uiState.notes,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun OsbNoteItemLists(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onItemClick: (Note) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notes,key = {it.id}) {
            OsbNoteItem(note = it, onItemClick = onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsbTopAppBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    searchActionClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = searchActionClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun OsbNoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onItemClick: (Note) -> Unit = {}
) {
    OutlinedCard(
        onClick = {
            onItemClick(note)
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    OnscriberTheme {
        OsbNoteItem(note = Note(1, "Title", "Content"))
    }
}