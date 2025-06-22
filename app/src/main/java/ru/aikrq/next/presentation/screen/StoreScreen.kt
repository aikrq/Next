package ru.aikrq.next.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.aikrq.next.presentation.component.StoreSectionTitle

@Composable
fun StoreScreen(
    padding: PaddingValues
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(padding)
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StoreSectionTitle(
            title = "Editor\'s Choice",
            onClick = {}
        )

        LazyRow(
            contentPadding =
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
        ) {

        }

        StoreSectionTitle(
            title = "Just updated",
            subtitle = "Fresh features & content",
            onClick = {}
        )

        LazyRow(
            contentPadding =
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
        ) {

        }

        StoreSectionTitle(
            title = "Trending",
            onClick = {}
        )

        LazyRow(
            contentPadding =
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
        ) {

        }

        StoreSectionTitle(
            title = "Most liked",
            onClick = {}
        )

        LazyRow(
            contentPadding =
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
        ) {

        }

        StoreSectionTitle(
            title = "Most downloaded",
            onClick = {}
        )

        LazyRow(
            contentPadding =
                WindowInsets.systemBars
                    .only(WindowInsetsSides.Horizontal)
                    .asPaddingValues()
        ) {

        }
    }
}