package com.centroi.alsuper.feature.fakeapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.repositories.ProductsRepository
import com.centroi.alsuper.core.data.extensions.EncryptionHelper
import com.centroi.alsuper.core.data.repositories.EmergencyContactsRepository
import com.centroi.alsuper.core.database.tables.EmergencyContact
import com.centroi.alsuper.core.database.tables.Product
import com.centroi.alsuper.feature.fakeapp.utils.mockProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    val products: StateFlow<List<Product>> = repository.getProducts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun loadMockProducts() {
        val mockProducts = mockProducts
        viewModelScope.launch {
            repository.insertData(mockProducts)
        }
    }
}