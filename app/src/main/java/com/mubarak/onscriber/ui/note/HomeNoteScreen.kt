package com.mubarak.onscriber.ui.note

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.mubarak.onscriber.R
import com.mubarak.onscriber.data.sources.local.model.Note
import com.mubarak.onscriber.ui.theme.OnscriberTheme

/** Stateful version for HomeScreen it uses [HomeNoteViewModel]*/
@Composable
fun OsbHomeScreen(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    onFabClick: () -> Unit,
    onSearchActionClick: () -> Unit = {},
    onItemClick: (Note) -> Unit = {},
    viewModel: HomeNoteViewModel = hiltViewModel()
) {

    OsbHomeScreen(
        modifier = modifier,
        onDrawerClick = onDrawerClick,
        notes = viewModel.uiState.notes,
        onFabClick = onFabClick,
        onSearchActionClick = onSearchActionClick,
        onItemClick = onItemClick
    )
}

/** Stateless version works wells in previews, and testing*/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsbHomeScreen(
    modifier: Modifier = Modifier,
    onDrawerClick: () -> Unit,
    notes: List<Note>,
    onFabClick: () -> Unit,
    onSearchActionClick: () -> Unit = {},
    onItemClick: (Note) -> Unit = {},
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), bottomBar = {
        BottomAppBar(actions = {
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
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, stringResource(id = R.string.create_note))
            }
        }, containerColor = Color.Transparent
        )
    }, topBar = {
        OsbTopAppBar(
            onMenuClick = {
                onDrawerClick()
            },
            searchActionClick = onSearchActionClick,
            scrollBehavior = scrollBehavior,
            modifier = modifier
        )
    }) {

        OsbNoteItemLists(
            modifier = Modifier.padding(it), notes = notes, onItemClick = onItemClick
        )
    }

}

@Composable
fun OsbNoteItemLists(
    modifier: Modifier = Modifier, notes: List<Note>, onItemClick: (Note) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(notes, key = { it.id }) {
            Box(modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)) {
                OsbNoteItem(note = it, onItemClick = onItemClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsbTopAppBar(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    searchActionClick: () -> Unit = {}
) {

    CenterAlignedTopAppBar(modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu, contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = searchActionClick) {
                Icon(
                    imageVector = Icons.Outlined.Search, contentDescription = null
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun OsbNoteItem(
    modifier: Modifier = Modifier, note: Note, onItemClick: (Note) -> Unit = {}
) {
    Card(
        onClick = {
            onItemClick(note)
        }, modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = note.title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = note.content,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OsbHomeScreenPreview() {
    OnscriberTheme {
        OsbHomeScreen(
            onDrawerClick = {},
            notes = listOf(Note(1,"Compose ❤\uFE0F","Android development")),
            onFabClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteItemPreview() {
    OnscriberTheme {
        OsbNoteItem(note = Note(1, "Title", "Content"))
    }
}