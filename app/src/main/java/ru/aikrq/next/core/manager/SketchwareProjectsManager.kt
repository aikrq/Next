/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.core.manager

import ru.aikrq.next.core.util.SketchwareProjectsUtil
import ru.aikrq.next.model.projects.ProjectsManager
import ru.aikrq.next.model.projects.SketchwareProject
import java.io.File

class SketchwareProjectsManager(private val sketchwareFolder: File) : ProjectsManager {
    /**
     * Gets projects.
     * @return List of [SketchwareProject].
     */
    override suspend fun getProjects() = SketchwareProjectsUtil.getProjectsList(sketchwareFolder)

    /**
     * Checks next free id and returns it.
     * @param startId - Specific id from which function
     * will check next free id. By default it is 601.
     * @return next free for new project id.
     */
    override suspend fun nextFreeId(startId: Int) =
        SketchwareProjectsUtil.nextFreeProjectId(File(sketchwareFolder, "mysc/list"))
}
