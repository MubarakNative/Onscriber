package com.mubarak.onscriber

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mubarak.onscriber.OsbDestination.HOME_DESTINATION
import com.mubarak.onscriber.OsbDestination.SETTINGS_DESTINATION
import com.mubarak.onscriber.OsbDestinationsArgs.NOTE_ID_ARG
import com.mubarak.onscriber.OsbDestinationsArgs.TITLE_ARG
import com.mubarak.onscriber.ui.addoredit.AddEditScreen
import com.mubarak.onscriber.ui.settings.OsbSettings
import com.mubarak.onscriber.ui.note.OsbHomeScreen

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
            }, onDrawerClick = onDrawerClicked)
        }

        composable(
            SETTINGS_DESTINATION
        ) {
            OsbSettings {
                onDrawerClicked()
            }
        }

        composable(
            OsbDestination.ADD_EDIT_DESTINATION,
            arguments = listOf(
                navArgument(TITLE_ARG) {
                    type = NavType.IntType
                },
                navArgument(NOTE_ID_ARG){
                    type = NavType.LongType
                    defaultValue = -1L
                }
        )
        ){ entry ->

        val appBarTitle = entry.arguments?.getInt(TITLE_ARG)!!

        AddEditScreen(onUpButtonClick = {
            navController.navigateUp()
        }, appBarTitle = appBarTitle)
    }
    }
}