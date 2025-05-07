package com.centroi.alsuper.core.data

import com.centroi.alsuper.core.data.di.DataModule
import com.centroi.alsuper.core.data.di.FakeEmergencyContactsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
interface TestDataModule {

    @Binds
    @Singleton
    fun bindFakeRepository(
        fakeRepo: FakeEmergencyContactsRepository
    ): EmergencyContactsRepository
}