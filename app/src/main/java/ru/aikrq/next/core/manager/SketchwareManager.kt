package ru.aikrq.next.core.manager

import ru.aikrq.next.model.projects.Manager
import java.io.File

/**
 * Basic class for access with sketchware.
 */
class SketchwareManager(private val sketchwareFolder: File) : Manager<SketchwareProjectsManager> {
    /**
     * @param folderPath - path to sketchware folder.
     */
    constructor(folderPath: String) : this(File(folderPath))

    /**
     * Sketchware projects manager for this instance.
     * Responsible for sketchware projects.
     */
    override val projectsManager = SketchwareProjectsManager(sketchwareFolder)
}