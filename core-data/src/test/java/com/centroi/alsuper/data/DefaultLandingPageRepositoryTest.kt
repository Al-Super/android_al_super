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

package com.centroi.alsuper.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.centroi.alsuper.core.data.DefaultLandingPageRepository
import com.centroi.alsuper.core.database.LandingPage
import com.centroi.alsuper.core.database.LandingPageDao

/**
 * Unit tests for [DefaultLandingPageRepository].
 */
class DefaultLandingPageRepositoryTest {

    @Test
    fun landingPages_newItemSaved_itemIsReturned() = runTest {
        val repository = DefaultLandingPageRepository(FakeLandingPageDao())

        repository.add("Repository")

        assertEquals(repository.landingPages.first().size, 1)
    }

}

private class FakeLandingPageDao : LandingPageDao {

    private val data = mutableListOf<LandingPage>()

    override fun getLandingPages(): Flow<List<LandingPage>> = flow {
        emit(data)
    }

    override suspend fun insertLandingPage(item: LandingPage) {
        data.add(0, item)
    }
}
