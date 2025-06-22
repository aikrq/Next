package ru.aikrq.next.model.projects

interface Manager<ProjectsManager> {
    /**
     * Sketchware projects manager for this instance.
     * Responsible for sketchware projects.
     */
    val projectsManager: ProjectsManager
}
