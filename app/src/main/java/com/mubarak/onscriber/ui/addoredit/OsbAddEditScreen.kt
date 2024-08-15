package com.mubarak.onscriber.ui.addoredit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mubarak.onscriber.R
import com.mubarak.onscriber.ui.theme.OnscriberTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    modifier: Modifier = Modifier,
    @StringRes appBarTitle: Int,
    onUpButtonClick: () -> Unit = {},
    viewModel: AddEditViewModel = hiltViewModel()
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    OnscriberTheme {
        Scaffold(
            modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            },
            topBar = {
                AddEditTopAppBar(
                    onUpButtonClick = onUpButtonClick,
                    scrollBehavior = scrollBehavior,
                    topAppBarTitle = appBarTitle,
                    actionDelete = viewModel::deleteNote
                )
            },
            floatingActionButton = {
                SaveFab(onFabClick = viewModel::saveNote)
            }) {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val currentOnBackPress by rememberUpdatedState(onUpButtonClick)

            DiaryNoteFields(
                modifier = Modifier.padding(it),
                title = viewModel.title,
                content = viewModel.content,
                onTitleChange = viewModel::updateTitle,
                onDescriptionChange = viewModel::updateContent
            )

            uiState.message?.let { msg ->
                val message = stringResource(id = msg)
                LaunchedEffect(message, viewModel, msg) {
                    snackBarHostState.showSnackbar(message)
                }
            }

            LaunchedEffect(uiState.navigateToHome) {
                if (uiState.navigateToHome) {
                    currentOnBackPress()
                }
            }
        }
    }
}

@Composable
fun DiaryNoteFields(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    onTitleChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {}
) {

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {

        val textFieldColour = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
        TextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.title),
                    style = TextStyle.Default.copy(fontSize = 24.sp)
                )
            },
            textStyle = TextStyle.Default.copy(fontSize = 24.sp),
            colors = textFieldColour
        )

        TextField(
            value = content,
            onValueChange = {
                onDescriptionChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.content),
                    style = TextStyle.Default.copy(fontSize = 17.sp)
                )
            },
            textStyle = TextStyle.Default.copy(fontSize = 17.sp),
            colors = textFieldColour
        )
    }

}

@Composable
fun SaveFab(modifier: Modifier = Modifier, onFabClick: () -> Unit) {
    FloatingActionButton(onClick = onFabClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.save)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    @StringRes topAppBarTitle: Int,
    onUpButtonClick: () -> Unit = {},
    actionDelete: () -> Unit = {},
) {
    TopAppBar(title = {
        Text(text = stringResource(id = topAppBarTitle))
    }, modifier = modifier, navigationIcon = {
        IconButton(onClick = onUpButtonClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.nav_back)
            )
        }
    }, actions = {
        IconButton(onClick = actionDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.delete_note)
            )
        }
    }, scrollBehavior = scrollBehavior)
}
