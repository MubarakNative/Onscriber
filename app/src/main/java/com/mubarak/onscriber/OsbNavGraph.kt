package com.mubarak.onscriber

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mubarak.onscriber.ui.addoredit.AddEditScreen
import com.mubarak.onscriber.ui.settings.OsbSettings
import com.mubarak.onscriber.ui.note.OsbHomeScreen

@Composable
fun OsbNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onDrawerClicked:() -> Unit,
    navActions: OsbNavActions = remember {
        OsbNavActions(navController)
    }
) {
    NavHost(
        navController = navController,
        startDestination = OsbDestination.HOME_DESTINATION
    ) {
        composable(
            OsbDestination.HOME_DESTINATION
        ){
            OsbHomeScreen(onFabClick = {
                navActions.navigateToAddEdit(0,"Add Note")
            }, onDrawerClick = onDrawerClicked)
        }

        composable(
            OsbDestination.SETTINGS_DESTINATION
        ){
            OsbSettings{
                onDrawerClicked()
            }
        }

        composable(
            OsbDestination.ADD_EDIT_DESTINATION
        ){
            AddEditScreen(onUpButtonClick = {
                navController.navigateUp()
            })
        }
    }
}