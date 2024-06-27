package com.mubarak.onscriber

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.mubarak.onscriber.OsbNavigation.HOME_ROUTE
import com.mubarak.onscriber.OsbNavigation.SETTINGS_ROUTE
import kotlinx.coroutines.launch

@Composable
fun OsbApp(
    widthSizeClass: WindowWidthSizeClass
) {

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val isExpanded = widthSizeClass == WindowWidthSizeClass.EXPANDED
    val sizeAwareDrawerState =
        rememberSizeAwareDrawerState(isExpanded) // allow swipe to open drawer based on size

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: HOME_ROUTE

    ModalNavigationDrawer(
        drawerContent = {
            OsbAppDrawer(
                navigateToHome = {
                    navController.navigate(HOME_ROUTE) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateToSettings = {
                    navController.navigate(SETTINGS_ROUTE) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
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
                AppNavRail(
                    currentScreen = currentScreen,
                    navigateToHome = {
                        navController.navigate(HOME_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, navigateToSettings = {
                        navController.navigate(SETTINGS_ROUTE) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })
            }
            OsbNavGraph(navController = navController, onDrawerClicked = {
                coroutineScope.launch {
                    sizeAwareDrawerState.apply {
                        if (isExpanded) {
                            close()
                        } else open()
                    }
                }
            })
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