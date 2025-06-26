/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.permission

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import ru.aikrq.next.core.manager.PermissionManager

class PermissionViewModel(application: Application) : AndroidViewModel(application) {
    val permissions = PermissionManager(application)
    val state = permissions.state

    fun onPermissionChange() {
        permissions.onPermissionChange()
    }

    fun checkPermissions() {
        permissions.checkPermissions()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun createSettingsIntent() =
        permissions.createSettingsIntent()
}