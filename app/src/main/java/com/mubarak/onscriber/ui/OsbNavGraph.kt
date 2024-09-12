package com.mubarak.onscriber.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mubarak.onscriber.R
import com.mubarak.onscriber.ui.addoredit.AddEditScreen
import com.mubarak.onscriber.ui.note.OsbHomeScreen
import com.mubarak.onscriber.ui.search.OsbSearchScreen
import com.mubarak.onscriber.ui.settings.OsbSettings

@Composable
fun OsbNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onDrawerClicked: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            OsbHomeScreen(onFabClick = {
                navController.navigate(AddEdit(-1L, R.string.create_note))
            }, onItemClick = {
                navController.navigate(AddEdit(it.id,R.string.edit_note))
            }, onSearchActionClick = {
                navController.navigate(Search)
            }, onDrawerClick = onDrawerClicked)
        }

        composable<Settings> {
            OsbSettings {
                onDrawerClicked()
            }
        }

        composable<Search> {
            OsbSearchScreen(onUpButtonClick = { navController.navigateUp() })
        }

        composable<AddEdit> { entry ->
            val addEdit: AddEdit = entry.toRoute()

            AddEditScreen(onUpButtonClick = {
                navController.navigateUp()
            }, appBarTitle = addEdit.title)
        }
    }
}