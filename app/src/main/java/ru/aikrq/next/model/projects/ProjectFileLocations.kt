package ru.aikrq.next.model.projects

import androidx.compose.runtime.Stable
import java.io.File

/**
 * Class with data about files where contains data/resources for some project.
 */
@Stable
class ProjectFilesLocations(
    /**
     * Project directory where "project" file located.
     */
    val projectMainDirectory: File? = null,
    /**
     * File with data about project.
     * @see ProjectConfigModel to see what info file contains.
     */
    val projectFile: File,
    /**
     * Folder with data about logic, view, project activities / custom views, etc.
     */
    val dataFolder: File,
    val resources: ProjectResourcesFiles
)

/**
 * Class with data about project resources.
 */
@Stable
class ProjectResourcesFiles(
    /**
     * Folder with all app icons (usually there only one icon with name app_icon.png).
     */
    val iconsFolder: File,
    /**
     * Folder with project's images.
     */
    val imagesFolder: File,
    /**
     * Folder with project's sounds
     */
    val soundsFolder: File,
    /**
     * Folder with project's fonts.
     */
    val fontsFolder: File
)