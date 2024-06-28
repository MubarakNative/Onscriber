package com.mubarak.onscriber

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.mubarak.onscriber.OsbDestinationsArgs.MESSAGE_ARG
import com.mubarak.onscriber.OsbDestinationsArgs.NOTE_ID_ARG
import com.mubarak.onscriber.OsbDestinationsArgs.TITLE_ARG
import com.mubarak.onscriber.OsbNavigation.ADD_EDIT_ROUTE
import com.mubarak.onscriber.OsbNavigation.HOME_ROUTE
import com.mubarak.onscriber.OsbNavigation.SETTINGS_ROUTE

object OsbNavigation {
    const val HOME_ROUTE = "home"
    const val SETTINGS_ROUTE = "settings"
    const val ADD_EDIT_ROUTE = "addedit"
}

object OsbDestinationsArgs {
    const val NOTE_ID_ARG = "noteId"
    const val TITLE_ARG = "title"
    const val MESSAGE_ARG = "message"
}

object OsbDestination {
    const val HOME_DESTINATION = "$HOME_ROUTE?$MESSAGE_ARG={$MESSAGE_ARG}"
    const val ADD_EDIT_DESTINATION = "${ADD_EDIT_ROUTE}/{$TITLE_ARG}?$NOTE_ID_ARG={$NOTE_ID_ARG}"
    const val SETTINGS_DESTINATION = SETTINGS_ROUTE
}

class OsbNavActions(private val navController: NavHostController) {

    fun navigateToHome(message: Int = 0) {
        val navigatesFromDrawer = message == 0

        navController.navigate(
            HOME_ROUTE.let {
                if (message != 0) "$it?$MESSAGE_ARG={$MESSAGE_ARG}" else it
            }
        ) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = !navigatesFromDrawer
                saveState = navigatesFromDrawer
            }
            launchSingleTop = true
            restoreState = navigatesFromDrawer
        }
    }

    fun navigateToAddEdit(noteId: Long, title: String) {
        navController.navigate(
            "$ADD_EDIT_ROUTE/$title".let {
                if (noteId != 0L) "$it?$NOTE_ID_ARG={$noteId}" else it
            }

        )
    }

    fun navigateToSettings(){
        navController.navigate(OsbDestination.SETTINGS_DESTINATION) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}