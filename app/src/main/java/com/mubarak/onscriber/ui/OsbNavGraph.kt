package com.mubarak.onscriber.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mubarak.onscriber.R
import com.mubarak.onscriber.ui.OsbDestination.HOME_DESTINATION
import com.mubarak.onscriber.ui.OsbDestination.SEARCH_DESTINATION
import com.mubarak.onscriber.ui.OsbDestination.SETTINGS_DESTINATION
import com.mubarak.onscriber.ui.OsbDestinationsArgs.NOTE_ID_ARG
import com.mubarak.onscriber.ui.OsbDestinationsArgs.TITLE_ARG
import com.mubarak.onscriber.ui.addoredit.AddEditScreen
import com.mubarak.onscriber.ui.note.OsbHomeScreen
import com.mubarak.onscriber.ui.search.OsbSearchScreen
import com.mubarak.onscriber.ui.settings.OsbSettings

@Composable
fun OsbNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onDrawerClicked: () -> Unit,
    navActions: OsbNavActions = remember {
        OsbNavActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = HOME_DESTINATION
    ) {
        composable(
            HOME_DESTINATION
        ) {
            OsbHomeScreen(onFabClick = {
                navActions.navigateToAddEdit(-1L, R.string.create_note)
            }, onItemClick = {
                navActions.navigateToAddEdit(it.id, R.string.edit_note)
            }, onSearchActionClick = {
                navActions.navigateToSearch()
            }, onDrawerClick = onDrawerClicked)
        }

        composable(
            SETTINGS_DESTINATION
        ) {
            OsbSettings {
                onDrawerClicked()
            }
        }

        composable(SEARCH_DESTINATION) {
            OsbSearchScreen(onUpButtonClick = { navController.navigateUp() })
        }

        composable(
            OsbDestination.ADD_EDIT_DESTINATION,
            arguments = listOf(
                navArgument(TITLE_ARG) {
                    type = NavType.IntType
                },
                navArgument(NOTE_ID_ARG) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { entry ->

            val appBarTitle = entry.arguments?.getInt(TITLE_ARG)!!

            AddEditScreen(onUpButtonClick = {
                navController.navigateUp()
            }, appBarTitle = appBarTitle)
        }
    }
}