/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.centroi.alsuper

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlSuper : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildTypeUtils.isDebug) {
            initLeakCanary()
        }
    }
}


object BuildTypeUtils {
    val isDebug: Boolean
        get() = isDebugBuild()

    private fun isDebugBuild(): Boolean {
        // This MUST live in the module where BuildConfig is accessible — typically `:app`
        return try {
            Class.forName("com.centroi.alsuper.BuildConfig")
                .getField("DEBUG")
                .getBoolean(null)
        } catch (e: Exception) {
            false
        }
    }
}