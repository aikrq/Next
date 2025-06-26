/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.presentation.screen.permission

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.aikrq.next.core.manager.PermissionManager
import javax.inject.Inject

@HiltViewModel
class PermissionViewModel @Inject constructor(@ApplicationContext context: Context) :
    ViewModel() {
    val permissions = PermissionManager(context)
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