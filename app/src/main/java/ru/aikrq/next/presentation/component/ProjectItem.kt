/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil3.compose.AsyncImage
import ru.aikrq.next.model.projects.ProjectConfigModel
import ru.aikrq.next.model.projects.ProjectFilesLocations
import ru.aikrq.next.presentation.theme.ADAPTIVE_BORDER_COLOR
import ru.aikrq.next.presentation.theme.ADAPTIVE_SURFACE_CONTAINER
import ru.aikrq.next.presentation.theme.ON_SURFACE_VARIANT_ALPHA
import java.io.File

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ProjectItem(
    model: ProjectConfigModel,
    locations: ProjectFilesLocations,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val providerPath = context.packageName + ".provider"
    val iconUri = remember(providerPath) {
        FileProvider.getUriForFile(
            context, providerPath, File(locations.resources.iconsFolder, "icon.png")
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                AsyncImage(
                    model = iconUri,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(60.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(ADAPTIVE_SURFACE_CONTAINER)
                        .border(
                            width = 0.5.dp,
                            color = ADAPTIVE_BORDER_COLOR,
                            shape = RoundedCornerShape(18.dp)
                        )
                )
                Text(
                    text = model.projectId,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = model.appName, style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = model.projectName,
                    color = ON_SURFACE_VARIANT_ALPHA,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = model.packageName,
                    color = ON_SURFACE_VARIANT_ALPHA,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Icon(
                imageVector = Icons.Default.ExpandMore,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable {}
                    .padding(12.dp))
        }
    }
}