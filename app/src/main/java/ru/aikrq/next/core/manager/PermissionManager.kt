/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.core.manager

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionManager(private val context: Context) {
    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
        )
    }

    data class State(
        val hasStorageAccess: Boolean,
        val hasFilesAccess: Boolean,
    )

    private val _state = MutableStateFlow(
        State(
            hasStorageAccess = hasAccess(READ_EXTERNAL_STORAGE) || hasAccess(WRITE_EXTERNAL_STORAGE),
            hasFilesAccess = hasFilesAccess()
        )
    )
    val state = _state.asStateFlow()

    val hasAllPermissions: Boolean
        get() = if (Build.VERSION.SDK_INT >= 30) {
            _state.value.hasStorageAccess && Environment.isExternalStorageManager()
        } else _state.value.hasStorageAccess

    private fun hasAccess(permission: String) =
        ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasFilesAccess() =
        if (Build.VERSION.SDK_INT >= 30) Environment.isExternalStorageManager() else true

    fun onPermissionChange() {
        val hasStorageAccess =
            hasAccess(READ_EXTERNAL_STORAGE) || hasAccess(WRITE_EXTERNAL_STORAGE)

        _state.value = State(
            hasStorageAccess = hasStorageAccess,
            hasFilesAccess = hasFilesAccess()
        )
    }

    fun checkPermissions() {
        val newState = State(
            hasStorageAccess = hasAccess(READ_EXTERNAL_STORAGE) || hasAccess(WRITE_EXTERNAL_STORAGE),
            hasFilesAccess = hasFilesAccess()
        )
        _state.value = newState
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun createSettingsIntent(): Intent {
        val intent = Intent()
        intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
        intent.data = "package:${context.packageName}".toUri()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return intent
    }
}