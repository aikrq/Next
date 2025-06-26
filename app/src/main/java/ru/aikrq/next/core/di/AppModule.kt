/*******************************************************************************
 * Copyright (c) 2025. Aikrq
 * All rights reserved.
 ******************************************************************************/

package ru.aikrq.next.core.di

import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.aikrq.next.core.manager.SketchwareManager
import java.io.File
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun getSketchwareManager(): SketchwareManager {
        return SketchwareManager(
            sketchwareFolder = File(
                Environment.getExternalStorageDirectory(),
                ".sketchware"
            )
        )
    }
}