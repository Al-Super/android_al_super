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

package com.centroi.alsuper.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.centroi.alsuper.core.data.LandingPageRepository
import com.centroi.alsuper.core.data.DefaultLandingPageRepository
import com.centroi.alsuper.core.data.EmergencyContactsRepository
import com.centroi.alsuper.core.data.EmergencyContactsRepositoryImpl
import com.centroi.alsuper.core.database.tables.EmergencyContact
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsLandingPageRepository(
        landingPageRepository: DefaultLandingPageRepository
    ): LandingPageRepository

    @Singleton
    @Binds
    fun bindsEmergencyContactsRepository(
        emergencyContactsRepository: EmergencyContactsRepositoryImpl
    ): EmergencyContactsRepository
}

class FakeLandingPageRepository @Inject constructor() : LandingPageRepository {
    override val landingPages: Flow<List<String>> = flowOf(fakeLandingPages)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

class FakeEmergencyContactsRepository @Inject constructor() : EmergencyContactsRepository {

    val insertedContacts = mutableListOf<EmergencyContact>()
    private val contacts = MutableStateFlow<List<EmergencyContact>>(emptyList())

    override fun getEmergencyContacts(): Flow<List<EmergencyContact>> = contacts

    override suspend fun insertContact(contact: EmergencyContact) {
        insertedContacts.add(contact)
        contacts.value = contacts.value + contact
    }

    override suspend fun deleteContact(contact: EmergencyContact) {
        contacts.value = contacts.value - contact
    }

    override suspend fun updateContact(contact: EmergencyContact) {
        contacts.value = contacts.value.map {
            if (it.id == contact.id) contact else it
        }
    }

    override suspend fun getContactById(contactId: Int): EmergencyContact? {
        return contacts.value.find { it.id == contactId }
    }
}

val fakeLandingPages = listOf("One", "Two", "Three")
