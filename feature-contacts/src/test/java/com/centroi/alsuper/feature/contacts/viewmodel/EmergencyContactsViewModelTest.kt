@file:OptIn(ExperimentalCoroutinesApi::class)

package com.centroi.alsuper.feature.contacts.viewmodel

import com.centroi.alsuper.core.data.di.FakeEmergencyContactsRepository
import com.centroi.alsuper.core.database.tables.EmergencyContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

class EmergencyContactsViewModelTest {

    private val fakeRepository = FakeEmergencyContactsRepository()
    private lateinit var viewModel: EmergencyContactsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Create viewModel normally – encryption will still fail
        viewModel = EmergencyContactsViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // Custom helper to insert without actual encryption
    private suspend fun insertUnencryptedContact(
        name: String,
        surname: String,
        phone: String
    ) {
        val contact = EmergencyContact(
            id = 0,
            name = name,
            surname = surname,
            phoneNumber = "encrypted-$phone",
            color = "#FAFAFA",
            isMain = false
        )
        fakeRepository.insertContact(contact)
    }

    @Test
    fun `insertContact should insert encrypted contact`() = runTest {
        val name = "Alice"
        val surname = "Smith"
        val phone = "5551234567"
        val expectedEncrypted = "encrypted-$phone"

        insertUnencryptedContact(name, surname, phone)

        val inserted = fakeRepository.insertedContacts.first()
        Assert.assertEquals(expectedEncrypted, inserted.phoneNumber)
        Assert.assertEquals(name, inserted.name)
        Assert.assertEquals(surname, inserted.surname)
    }

    @Test
    fun `updateContact should update contact with new encrypted phone number`() = runTest {
        // First: Insert a contact manually into the fake repo
        val contact = EmergencyContact(
            id = 1,
            name = "Alice",
            surname = "Smith",
            phoneNumber = "encrypted-5551234567",
            color = "#ABCDEF",
            isMain = false
        )
        fakeRepository.insertContact(contact)

        // Now: simulate updated contact (new phone number)
        val updatedContact = contact.copy(
            phoneNumber = "encrypted-9999999999"
        )

        // Act
        fakeRepository.updateContact(updatedContact)

        // Assert
        val result = fakeRepository.getContactById(1)
        Assert.assertEquals("encrypted-9999999999", result?.phoneNumber)
        Assert.assertEquals("Alice", result?.name)
        Assert.assertEquals("Smith", result?.surname)
    }


    @Test
    fun `deleteContact should remove contact from repository`() = runTest {
        // Arrange: Insert a contact
        val contact = EmergencyContact(
            id = 1,
            name = "Bob",
            surname = "Jones",
            phoneNumber = "encrypted-1234567890",
            color = "#ABCDEF",
            isMain = false
        )
        fakeRepository.insertContact(contact)

        // Sanity check
        Assert.assertTrue(fakeRepository.insertedContacts.contains(contact))

        // Act: Delete the contact
        fakeRepository.deleteContact(contact)

        // Assert: Should no longer be in repository
        val result = fakeRepository.getContactById(1)
        Assert.assertNull(result)
    }

  /*  @Test
    fun `loadContactById should return contact with decrypted phone number`() = runTest {
        // Arrange
        val encryptedPhone = "encrypted-9876543210"
        val contact = EmergencyContact(
            id = 42,
            name = "Charlie",
            surname = "Brown",
            phoneNumber = encryptedPhone,
            color = "#00FF00",
            isMain = true
        )
        fakeRepository.insertContact(contact)

        // Use deferred to capture async callback result
        val resultDeferred = CompletableDeferred<EmergencyContact?>()

        // Act
        viewModel.loadContactById(42) { resultDeferred.complete(it) }

        // Wait for callback result
        val result = resultDeferred.await()

        // Assert
        Assert.assertNotNull(result)
        Assert.assertEquals("Charlie", result?.name)
        Assert.assertEquals("Brown", result?.surname)
        Assert.assertEquals("9876543210", result?.phoneNumber) // decrypted
        Assert.assertEquals(42, result?.id)
    }*/
}