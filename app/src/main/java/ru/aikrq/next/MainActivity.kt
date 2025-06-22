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
import ru.aikrq.next.presentation.screen.Design
import ru.aikrq.next.presentation.screen.Main
import ru.aikrq.next.presentation.screen.MainScreen
import ru.aikrq.next.presentation.screen.Settings
import ru.aikrq.next.presentation.screen.SettingsScreen
import ru.aikrq.next.presentation.theme.NextTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            val backStack = rememberNavBackStack(Main)

            NextTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<Main> { MainScreen(
                            onSettingsClick = { backStack.add(Settings) }
                        ) }
                        entry<Design> {}
                        entry<Settings> { SettingsScreen(
                            onBackClick = { backStack.removeLastOrNull() }
                        ) }
                    })
            }
        }
    }
}