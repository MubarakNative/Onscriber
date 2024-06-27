package com.mubarak.onscriber

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mubarak.onscriber.OsbNavigation.HOME_ROUTE
import com.mubarak.onscriber.OsbNavigation.SETTINGS_ROUTE

object OsbNavigation {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
}


class OsbNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(HOME_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(SETTINGS_ROUTE) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
