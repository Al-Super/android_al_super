package com.centroi.alsuper.core.data.usecases.fakearticle

import com.centroi.alsuper.core.data.models.fakearticle.CartItemData
import com.centroi.alsuper.core.data.repositories.fakearticles.CartRepositoryInt
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CartRepositoryInt
) {
    operator fun invoke() = repository.getCartItems().map { list ->
        list.map { item ->
            CartItemData(
                productId = item.productId,
                name = item.name,
                price = item.price,
                imageUrl = item.imageUrl,
                quantity = item.quantity
            )
        }
    }
}