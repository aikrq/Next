package ru.aikrq.next.core.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

fun Context.requestAllFilesPermissions() {
    if (Build.VERSION.SDK_INT >= 30) {
        if (!Environment.isExternalStorageManager()) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            intent.data = "package:$packageName".toUri()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            try {
                startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                Log.e("FileUtil", "Activity to manage apps' all files access permission not found!")
            }
        }
    } else {
        error("Not on an API level 30 or higher device!")
    }
}

suspend fun File.getFiles(): List<File> = getFilesOrEmpty().also {
    if (it.isEmpty()) error("error while getting files")
}

suspend fun File.getFilesOrEmpty() = withContext(Dispatchers.IO) {
    return@withContext listFiles()?.toList() ?: emptyList()
}

fun File.read() = readBytes()

suspend fun File.write(bytes: ByteArray) = withContext(Dispatchers.IO) {
    return@withContext writeBytes(bytes)
}

suspend fun File.remove() = withContext(Dispatchers.IO) {
    return@withContext if (isFile) delete() else deleteRecursively()
}

suspend fun removeFiles(vararg files: File) = files.forEach {
    it.remove()
}

suspend fun File.createFolder(): Boolean = withContext(Dispatchers.IO) {
    mkdirs()
}