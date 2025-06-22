/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialExpressiveTheme

val ON_SURFACE_VARIANT_ALPHA
    @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f)

val ADAPTIVE_SURFACE_CONTAINER
    @Composable get() = MaterialTheme.colorScheme.surfaceContainer onDark MaterialTheme.colorScheme.surfaceContainerLow

val ADAPTIVE_BORDER_COLOR
    @Composable get() = MaterialTheme.colorScheme.surfaceContainerHighest onDark MaterialTheme.colorScheme.surfaceContainerHigh

@Composable
infix fun Color.onDark(darkColor: Color): Color =
    if (isSystemInDarkTheme()) darkColor else this

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun NextTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit
) {
    DynamicMaterialExpressiveTheme(
        seedColor = Color(0xFFFE6262),
        motionScheme = MotionScheme.expressive(),
        isDark = darkTheme,
        animate = true,
        typography = Typography,
        content = { Surface(content = content) }
    )
}