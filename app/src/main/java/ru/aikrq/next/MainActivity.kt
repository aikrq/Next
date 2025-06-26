/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import dagger.hilt.android.AndroidEntryPoint
import ru.aikrq.next.core.manager.PermissionManager
import ru.aikrq.next.presentation.screen.Main
import ru.aikrq.next.presentation.screen.MainScreen
import ru.aikrq.next.presentation.screen.Permission
import ru.aikrq.next.presentation.screen.PermissionScreen
import ru.aikrq.next.presentation.screen.Settings
import ru.aikrq.next.presentation.screen.SettingsScreen
import ru.aikrq.next.presentation.theme.NextTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var permissionManager: PermissionManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.auto(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        super.onCreate(savedInstanceState)
        permissionManager = PermissionManager(this)

        setContent {
            val startNavigation =
                if (permissionManager.hasAllPermissions) {
                    Main
                } else {
                    Permission
                }
            val backStack = rememberNavBackStack(startNavigation)

            NextTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<Main> {
                            MainScreen(
                                onSettingsClick = { backStack.add(Settings) }
                            )
                        }
                        entry<Permission> {
                            PermissionScreen(onPermissionGiven = {
                                backStack.removeLastOrNull()
                                backStack.add(Main)
                            })
                        }
                        entry<Settings> {
                            SettingsScreen(
                                onBackClick = { backStack.removeLastOrNull() }
                            )
                        }
                    })
            }
        }
    }
}