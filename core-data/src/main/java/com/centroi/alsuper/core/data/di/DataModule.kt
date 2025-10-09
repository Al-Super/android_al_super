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

import com.centroi.alsuper.core.data.models.LandingPageData
import com.centroi.alsuper.core.data.models.LandingPageName
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.centroi.alsuper.core.data.repositories.landingPage.LandingPageRepository
import com.centroi.alsuper.core.data.repositories.landingPage.DefaultLandingPageRepository
import com.centroi.alsuper.core.data.repositories.EmergencyContactsRepository
import com.centroi.alsuper.core.data.repositories.EmergencyContactsRepositoryImpl
import com.centroi.alsuper.core.data.repositories.LocationRepository
import com.centroi.alsuper.core.data.repositories.LocationRepositoryImpl
import com.centroi.alsuper.core.data.repositories.chat.ChatRepository
import com.centroi.alsuper.core.data.repositories.chat.ChatRepositoryInt
import com.centroi.alsuper.core.data.repositories.landingPage.LandingPageDataProviderImpl
import com.centroi.alsuper.core.data.repositories.landingPage.LandingPageDataProviderInt
import com.centroi.alsuper.core.data.repositories.login.LoginRepository
import com.centroi.alsuper.core.data.repositories.login.LoginRepositoryInt
import com.centroi.alsuper.core.data.repositories.login.ProfileRepository
import com.centroi.alsuper.core.data.repositories.login.ProfileRepositoryInt
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

    @Singleton
    @Binds
    fun bindLocationRepository(
        locationRepository: LocationRepositoryImpl
    ): LocationRepository

    @Singleton
    @Binds
    fun bindLandingPageDataProvider(
        impl: LandingPageDataProviderImpl
    ): LandingPageDataProviderInt

    @Singleton
    @Binds
    fun bindLoginRepository(
        impl: LoginRepository
    ): LoginRepositoryInt

    @Singleton
    @Binds
    fun bindChatRepository(
        impl: ChatRepository
    ): ChatRepositoryInt

    @Singleton
    @Binds
    fun bindProfileRepository(
        impl: ProfileRepository
    ): ProfileRepositoryInt
}

class FakeLandingPageRepository @Inject constructor() : LandingPageRepository {
    override val landingPages: Flow<List<LandingPageData>> = flowOf(listOf(fakeLandingPages))

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

val fakeLandingPages = LandingPageData(
    pageID = LandingPageName.LANDING_PAGE_FIRST,
    buttonClose = "Close mode",
    title = "Title",
    description = "description",
    text = "Text",
    checkBoxTermsAndConditions = "Terms and Conditions" ,
    checkBoxDataConsent = "Data Consent",
    consentNeeded = false
)
