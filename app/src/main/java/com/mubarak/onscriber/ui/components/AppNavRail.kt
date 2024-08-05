package com.mubarak.onscriber.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mubarak.onscriber.ui.OsbDestination
import com.mubarak.onscriber.R

@Composable
fun AppNavRail(
    modifier: Modifier = Modifier,
    currentScreen: Any,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit
) {
    NavigationRail(modifier = modifier) {

        Spacer(modifier = Modifier.weight(1f))
        NavigationRailItem(
            selected = currentScreen == OsbDestination.HOME_DESTINATION,
            onClick = navigateToHome,
            icon = {
                Icon(Icons.AutoMirrored.Filled.List, stringResource(id = R.string.notes))
            },
            label = {
                Text(stringResource(id = R.string.notes))
            },
            alwaysShowLabel = false
        )

        NavigationRailItem(
            selected = currentScreen == OsbDestination.SETTINGS_DESTINATION,
            onClick = navigateToSettings,
            icon = {
                Icon(Icons.Default.Settings, stringResource(id = R.string.settings))
            },
            label = {
                Text(stringResource(id = R.string.settings))
            },
            alwaysShowLabel = false
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}