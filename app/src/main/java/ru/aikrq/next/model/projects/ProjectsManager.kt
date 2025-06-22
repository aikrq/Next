package ru.aikrq.next.model.projects

interface ProjectsManager {
    /**
     * Gets projects.
     * @return List of [SketchwareProject].
     */
    suspend fun getProjects(): List<SketchwareProject>

    /**
     * Checks next free id and returns it.
     * @param startId - Specific id from which function
     * will check next free id. By default it is 601.
     * @return next free for new project id.
     */
    suspend fun nextFreeId(startId: Int): Int
}