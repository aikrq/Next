/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import ru.aikrq.next.presentation.screen.projects.ProjectsScreen
import ru.aikrq.next.presentation.theme.ADAPTIVE_BORDER_COLOR
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER
import ru.aikrq.next.presentation.util.materialSharedAxisZ

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    onSettingsClick: () -> Unit,
) {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }
    val mainBackStack = remember { MainBackStack<Any>(Projects) }
    val searchBarState = rememberSearchBarState()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            state = textFieldState,
            onSearch = { expanded = false },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            placeholder = { Text("Search for projects") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            },
            colors = SearchBarDefaults.inputFieldColors(
                unfocusedContainerColor = ADAPTIVE_SURFACE_CONTAINER,
                focusedContainerColor = ADAPTIVE_SURFACE_CONTAINER
            )
        )
    }

    Scaffold(
        topBar = {
            TopSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                inputField = inputField,
                state = searchBarState
            )
        }, bottomBar = {
            Column {
                HorizontalDivider(
                    color = ADAPTIVE_BORDER_COLOR
                )
                NavigationBar(
                    containerColor = ADAPTIVE_SURFACE_CONTAINER
                ) {
                    MAIN_ROUTES.forEach { route ->
                        val isSelected = route == mainBackStack.topLevelKey

                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                indicatorColor = MaterialTheme.colorScheme.surfaceContainerHighest
                            ), selected = isSelected, onClick = {
                                mainBackStack.addTopLevel(route)
                            }, icon = {
                                Icon(
                                    modifier = Modifier.size(24.dp),
                                    painter = route.icon.asPainter(),
                                    contentDescription = null
                                )
                            }, label = {
                                Text(route.label)
                            })
                    }
                }
            }
        }, containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        val transition = materialSharedAxisZ(true)
        NavDisplay(
            backStack = mainBackStack.backStack,
            onBack = { mainBackStack.removeLast() },
            transitionSpec = { transition },
            popTransitionSpec = { transition },
            entryProvider = entryProvider {
                entry<Projects> { ProjectsScreen(innerPadding) }
                entry<Store> { StoreScreen(innerPadding) }
            })
    }
}

class MainBackStack<T : Any>(startKey: T) {
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)

    private fun updateBackStack() = backStack.apply {
        clear()
        addAll(topLevelStacks.flatMap { it.value })
    }

    fun addTopLevel(key: T) {
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            topLevelStacks.apply { remove(key)?.let { put(key, it) } }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun removeLast() {
        val removedKey = topLevelStacks[topLevelKey]?.removeLastOrNull()
        topLevelStacks.remove(removedKey)
        topLevelKey = topLevelStacks.keys.last()
        updateBackStack()
    }
}