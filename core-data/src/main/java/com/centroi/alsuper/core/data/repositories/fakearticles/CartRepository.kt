package com.centroi.alsuper.core.data.repositories.fakearticles

import com.centroi.alsuper.core.database.tables.CartItem
import com.centroi.alsuper.core.database.tables.CartItemDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CartRepositoryInt {
    suspend fun addToCart(item: CartItem)
    suspend fun deleteCartItem(id: String)
    fun getCartItems(): Flow<List<CartItem>>
}

class CartRepository @Inject constructor(
    private val cartItemDao: CartItemDao
): CartRepositoryInt {

    override suspend fun addToCart(item: CartItem) = cartItemDao.insertCartItem(item)

    override suspend fun deleteCartItem(id: String) {
        cartItemDao.deleteCartItemByProductId(id)
    }

    override fun getCartItems(): Flow<List<CartItem>> = cartItemDao.getAllCartItems()

}