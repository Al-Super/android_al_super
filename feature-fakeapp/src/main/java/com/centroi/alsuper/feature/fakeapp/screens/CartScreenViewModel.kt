package com.centroi.alsuper.feature.fakeapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.centroi.alsuper.core.data.models.fakearticle.CartItemData
import com.centroi.alsuper.core.data.usecases.fakearticle.AddProductUseCase
import com.centroi.alsuper.core.data.usecases.fakearticle.DeleteArticleUseCase
import com.centroi.alsuper.core.data.usecases.fakearticle.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CartScreenViewModelAbstract(): ViewModel() {

    abstract fun addProduct(item: CartItemData)
    abstract fun deleteProduct(id: String)

    abstract val cartItems: StateFlow<List<CartItemData>>

}

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val getCartItemsUseCase: GetCartUseCase,
    private val deleteCartItemUseCase: DeleteArticleUseCase
): CartScreenViewModelAbstract() {

    private val _cartItems = MutableStateFlow<List<CartItemData>>(emptyList())
    override val cartItems: StateFlow<List<CartItemData>> = _cartItems.asStateFlow()

    init {
        getCartItems()
    }

    private fun getCartItems() {
        getCartItemsUseCase().onEach { items ->
            _cartItems.value = items
        }.launchIn(viewModelScope)
    }

    override fun addProduct(item: CartItemData) {
        viewModelScope.launch {
            addProductUseCase.invoke(item)
        }
    }

    override fun deleteProduct(id: String) {
        viewModelScope.launch {
            deleteCartItemUseCase.invoke(id)
            getCartItems()
        }
    }

}