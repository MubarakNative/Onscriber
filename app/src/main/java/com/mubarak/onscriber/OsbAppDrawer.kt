package com.mubarak.onscriber

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mubarak.onscriber.ui.theme.OnscriberTheme

@Composable
fun OsbAppDrawer(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToSettings: () -> Unit,
    closeDrawer: () -> Unit,
    currentScreen: String
) {
    ModalDrawerSheet(
        modifier = modifier
    ) {
        NavigationHeader()
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(id = R.string.notes))
            },
            selected = currentScreen == OsbNavigation.HOME_ROUTE,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(
                        id = R.string.notes
                    )
                )
            },
            onClick = {navigateToHome(); closeDrawer()}
        )
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(id = R.string.settings))
            },
            selected = currentScreen == OsbNavigation.SETTINGS_ROUTE,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(
                        id = R.string.settings
                    )
                )
            },
            onClick = {navigateToSettings(); closeDrawer()}
        )
    }
}

@Composable
fun NavigationHeader(modifier: Modifier = Modifier) {

    Box(modifier = modifier.padding(horizontal = 28.dp, vertical = 24.dp)) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = modifier,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NavHeaderPreview() {
    OnscriberTheme {
        NavigationHeader()
    }
}