package ru.aikrq.next.presentation.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.aikrq.next.R
import ru.aikrq.next.presentation.util.IconResource

val MAIN_ROUTES = listOf<MainRoute>(Projects, Store)

sealed interface MainRoute {
    val icon: IconResource
    val label: String
}

@Serializable
data object Main : NavKey

@Serializable
data object Projects : MainRoute, NavKey {
    override val icon: IconResource = IconResource.fromImageVector(Icons.AutoMirrored.Filled.List)
    override val label: String = "Projects"
}

@Serializable
data object Store : MainRoute, NavKey {
    override val icon: IconResource =
        IconResource.fromDrawableResource(R.drawable.sketchub_logo_letter)
    override val label: String = "Store"
}

@Serializable
data object Settings : NavKey

@Serializable
data class Design(val scId: String) : NavKey
