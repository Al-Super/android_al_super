package com.centroi.alsuper.core.data.usecases.fakearticle

import com.centroi.alsuper.core.data.models.fakearticle.CartItemData
import com.centroi.alsuper.core.data.repositories.fakearticles.CartRepositoryInt
import com.centroi.alsuper.core.database.tables.CartItem
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val repository: CartRepositoryInt
) {
    suspend operator fun invoke(item: CartItemData) = repository.addToCart(
        CartItem(
            productId = item.productId,
            name = item.name,
            price = item.price,
            imageUrl = item.imageUrl,
            quantity = item.quantity
        )
    )
}