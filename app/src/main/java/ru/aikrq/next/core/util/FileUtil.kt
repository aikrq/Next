/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.core.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

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