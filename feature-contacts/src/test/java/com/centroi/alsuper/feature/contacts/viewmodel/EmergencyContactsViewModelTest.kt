package com.centroi.alsuper.feature.contacts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.centroi.alsuper.core.data.EmergencyContactsRepository
import com.centroi.alsuper.core.data.extensions.EncryptionHelper
import com.centroi.alsuper.core.database.tables.EmergencyContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
//@RunWith(RobolectricTestRunner::class)
class EmergencyContactsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: EmergencyContactsRepository

    private lateinit var viewModel: EmergencyContactsViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this) // Initialize the mocks
        Dispatchers.setMain(testDispatcher)
        viewModel = EmergencyContactsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test delete contact`() = runTest {
        val contact = EmergencyContact(1, "John", "Doe", "encryptedPhone", "#FFFFFF", false)

        viewModel.deleteContact(contact)

        Mockito.verify(repository).deleteContact(contact)
    }

   /* @Test
    fun `test update contact`() = runTest {
        val contact = EmergencyContact(1, "John", "Doe", "1234567890", "#FFFFFF", false)
        val encryptedPhoneNumber = "encryptedPhone"

        Mockito.`when`(EncryptionHelper.encrypt(contact.phoneNumber)).thenReturn(encryptedPhoneNumber)

        viewModel.updateContact(contact)

        val updatedContact = contact.copy(phoneNumber = encryptedPhoneNumber)
        Mockito.verify(repository).updateContact(updatedContact)
    }

    @Test
    fun `test load contact by id`() = runTest {
        val contactId = 1
        val encryptedContact = EmergencyContact(contactId, "John", "Doe", "encryptedPhone", "#FFFFFF", false)
        val decryptedContact = encryptedContact.copy(phoneNumber = "decryptedPhone")

        Mockito.`when`(repository.getContactById(contactId)).thenReturn(encryptedContact)
        Mockito.`when`(EncryptionHelper.decrypt("encryptedPhone")).thenReturn("decryptedPhone")

        val onResult: (EmergencyContact?) -> Unit = mock()
        viewModel.loadContactById(contactId, onResult)

        Mockito.verify(onResult).invoke(decryptedContact)
    }*/
}