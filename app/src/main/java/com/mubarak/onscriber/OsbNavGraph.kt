package com.mubarak.onscriber

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mubarak.onscriber.ui.settings.OsbSettings
import com.mubarak.onscriber.ui.note.OsbHomeScreen

@Composable
fun OsbNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onDrawerClicked:() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = OsbNavigation.HOME_ROUTE
    ) {
        composable(
            OsbNavigation.HOME_ROUTE
        ){
            OsbHomeScreen {
                onDrawerClicked()
            }
        }

        composable(
            OsbNavigation.SETTINGS_ROUTE
        ){
            OsbSettings{
                onDrawerClicked()
            }
        }
    }
}