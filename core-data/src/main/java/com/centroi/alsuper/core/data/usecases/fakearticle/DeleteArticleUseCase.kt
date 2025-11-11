package com.centroi.alsuper.core.data.usecases.fakearticle

import com.centroi.alsuper.core.data.models.fakearticle.CartItemData
import com.centroi.alsuper.core.data.repositories.fakearticles.CartRepositoryInt
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeleteArticleUseCase @Inject constructor(
    private val repository: CartRepositoryInt
) {
    suspend operator fun invoke(id: String) = repository.deleteCartItem(id)
}