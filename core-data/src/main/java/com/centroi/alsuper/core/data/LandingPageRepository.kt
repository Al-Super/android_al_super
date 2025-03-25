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

package com.centroi.alsuper.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.centroi.alsuper.core.database.LandingPage
import com.centroi.alsuper.core.database.LandingPageDao
import javax.inject.Inject

interface LandingPageRepository {
    val landingPages: Flow<List<String>>

    suspend fun add(name: String)
}

class DefaultLandingPageRepository @Inject constructor(
    private val landingPageDao: LandingPageDao
) : LandingPageRepository {

    override val landingPages: Flow<List<String>> =
        landingPageDao.getLandingPages().map { items -> items.map { it.name } }

    override suspend fun add(name: String) {
        landingPageDao.insertLandingPage(LandingPage(name = name))
    }
}
