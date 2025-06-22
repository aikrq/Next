package ru.aikrq.next.model.projects

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import ru.aikrq.next.core.util.SketchwareEncryptor.decrypt
import ru.aikrq.next.core.util.SketchwareEncryptor.encrypt
import ru.aikrq.next.core.util.remove
import ru.aikrq.next.core.util.read
import ru.aikrq.next.core.util.removeFiles
import ru.aikrq.next.core.util.deserialize
import ru.aikrq.next.core.util.serialize
import ru.aikrq.next.core.util.write

@Stable
class SketchwareProject(
    val locations: ProjectFilesLocations
) {
    /**
     * Returns config with data about sketchware project.
     * @return [ProjectConfigModel] with data about project.
     * @see ProjectConfigModel
     */
    fun getConfig() =
        locations.projectFile.read().decrypt().byteArrayToString().serialize<ProjectConfigModel>()

    /**
     * Edits sketchware project config.
     * @param editor lambda with [ProjectConfigModel] in context.
     */
    suspend fun editConfig(editor: ProjectConfigModel.() -> Unit) =
        locations.projectFile.write(
            getConfig().apply(editor).deserialize().toByteArray().encrypt()
        )

    /**
     * Deletes all project files.
     */
    suspend fun delete() {
        locations.dataFolder.remove()
        locations.projectFile.remove()
        locations.projectMainDirectory?.remove()
        locations.resources.apply {
            removeFiles(imagesFolder, iconsFolder, fontsFolder, soundsFolder)
        }
    }
}

private fun ByteArray.byteArrayToString() = String(this)
