package com.mubarak.onscriber

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.launch

@Composable
fun OsbApp(
    widthSizeClass: WindowWidthSizeClass
) {

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val navActions = remember(navController) {
        OsbNavigationActions(navController)
    }
    val isExpanded = widthSizeClass == WindowWidthSizeClass.EXPANDED
    val sizeAwareDrawerState =
        rememberSizeAwareDrawerState(isExpanded) // allow swipe to open drawer based on size

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: OsbHome

    ModalNavigationDrawer(
        drawerContent = {
            OsbAppDrawer(
                navigateToHome = {
                    navController.navigate(OsbHome)
                    Log.i("OnSettingsClick", "Home clickdd")

                },
                navigateToSettings = {
                   navController.navigate(OsbSettings)
                    Log.i("OnSettingsClick", "Settings clickdd")
                  //  Toast.makeText(context, "SC", Toast.LENGTH_SHORT).show()
                },
                currentScreen = currentScreen,
                closeDrawer = {
                    coroutineScope.launch {
                        sizeAwareDrawerState.close()
                    }
                }
            )
        }, gesturesEnabled = !isExpanded,
        drawerState = sizeAwareDrawerState
    ) { // Content
        Row {
            if (isExpanded) {
                AppNavRail(currentScreen = currentScreen, navigateToHome = {
                    //navActions.navigateToHome
                    navController.navigate(OsbHome)
                }, navigateToSettings = {
                   // navActions.navigateToSettings
                    navController.navigate(OsbSettings)
                })
            } else {
                OsbNavGraph(navController = navController) {
                    coroutineScope.launch {
                        sizeAwareDrawerState.open()
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        drawerState
    } else {
        DrawerState(DrawerValue.Closed)
    }
}