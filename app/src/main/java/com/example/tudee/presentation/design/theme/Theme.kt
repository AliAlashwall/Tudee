package com.example.tudee.presentation.design.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.example.tudee.presentation.design.color.LocalTudeeColors
import com.example.tudee.presentation.design.color.TudeeColors
import com.example.tudee.presentation.design.color.darkThemeColors
import com.example.tudee.presentation.design.color.lightThemeColors
import com.moscow.tudee.presentation.designSystem.theme.ThemeState
import com.example.tudee.presentation.design.typography.DefaultTextStyle
import com.example.tudee.presentation.design.typography.LocalTudeeTextStyle
import com.example.tudee.presentation.design.typography.TudeeTextStyle


@Composable
fun TudeeTheme(
    state: ThemeState = ThemeState(isDark = isSystemInDarkTheme(), onThemeChanged = {}),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        state.isDark -> darkThemeColors
        else -> lightThemeColors
    }
    CompositionLocalProvider(
        LocalThemeState provides state,
        LocalTudeeColors provides colorScheme,
        LocalTudeeTextStyle provides DefaultTextStyle,
    ) {
        content()
    }
}

object Theme {
    val colors: TudeeColors
        @Composable @ReadOnlyComposable get() = LocalTudeeColors.current

    val textStyle: TudeeTextStyle
        @Composable @ReadOnlyComposable get() = LocalTudeeTextStyle.current

    val state: ThemeState
        @Composable get() = LocalThemeState.current
}

val LocalThemeState = compositionLocalOf { ThemeState(false, {}) }