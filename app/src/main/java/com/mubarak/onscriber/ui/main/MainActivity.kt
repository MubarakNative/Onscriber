package com.mubarak.onscriber.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import com.mubarak.onscriber.OsbApp
import com.mubarak.onscriber.ui.theme.OnscriberTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnscriberTheme {
                val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                OsbApp(windowWidthSizeClass.windowWidthSizeClass)
            }
        }
    }
}
