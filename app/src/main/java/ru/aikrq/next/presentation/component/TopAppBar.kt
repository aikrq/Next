package ru.aikrq.next.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.aikrq.next.presentation.theme.ADAPTIVE_BORDER_COLOR
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER
import ru.aikrq.next.presentation.theme.ON_SURFACE_VARIANT_ALPHA
import ru.aikrq.next.presentation.theme.onDark

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TopAppBar(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    navigationIconOnClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    titleHorizontalAlignment: Alignment.Horizontal = Alignment.Start,
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        subtitleContentColor = ON_SURFACE_VARIANT_ALPHA
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(modifier = Modifier.padding(start = 12.dp), text = title)
        },
        subtitle = {
            subtitle?.let { Text(modifier = Modifier.padding(start = 12.dp), text = subtitle) }
        },
        modifier = modifier,
        navigationIcon = {
            navigationIcon?.let {
                val interactionSource = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ADAPTIVE_SURFACE_CONTAINER, CircleShape)
                        .border(width = 0.5.dp, color = ADAPTIVE_BORDER_COLOR, CircleShape)
                        .clickable(
                            role = Role.Button,
                            interactionSource = interactionSource,
                            indication = ripple(),
                            onClick = navigationIconOnClick
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = navigationIcon, contentDescription = null
                    )
                }
            }
        },
        actions = actions,
        titleHorizontalAlignment = titleHorizontalAlignment,
        expandedHeight = expandedHeight,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}