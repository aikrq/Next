/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.core.manager

import ru.aikrq.next.model.projects.Manager
import java.io.File

/**
 * Basic class for access with sketchware.
 */
class SketchwareManager(sketchwareFolder: File) : Manager<SketchwareProjectsManager> {
    /**
     * Sketchware projects manager for this instance.
     * Responsible for sketchware projects.
     */
    override val projectsManager = SketchwareProjectsManager(sketchwareFolder)
}