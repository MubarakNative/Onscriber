package com.mubarak.onscriber

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

object OsbNavigation {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
}

@Serializable
object OsbHome

@Serializable
object OsbSettings

class OsbNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(OsbHome) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToSettings: () -> Unit = {
        navController.navigate(OsbSettings) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
