package ru.aikrq.next.core.util

import ru.aikrq.next.model.projects.ProjectFilesLocations
import ru.aikrq.next.model.projects.ProjectResourcesFiles
import ru.aikrq.next.model.projects.SketchwareProject
import java.io.File

object SketchwareProjectsUtil {
    suspend fun getProjectsList(baseFolder: File) = File(baseFolder, "mysc/list").getFiles().map {
        return@map SketchwareProject(
            ProjectFilesLocations(
                projectMainDirectory = it,
                projectFile = File(it, "project"),
                dataFolder = File(baseFolder, "data/${it.name}"),
                resources = ProjectResourcesFiles(
                    File(baseFolder, "resources/icons/${it.name}"),
                    File(baseFolder, "images/${it.name}"),
                    File(baseFolder, "resources/sounds/${it.name}"),
                    File(baseFolder, "resources/fonts/${it.name}")
                )
            )
        )
    }

    suspend fun nextFreeProjectId(
        listFolder: File, startId: Int = 601
    ): Int = if (listFolder.getFiles().any { it.name == startId.toString() }) nextFreeProjectId(
        listFolder, startId + 1
    )
    else startId
}
