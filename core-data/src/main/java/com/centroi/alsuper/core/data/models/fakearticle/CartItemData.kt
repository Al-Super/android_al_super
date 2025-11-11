package com.centroi.alsuper.core.data.models.fakearticle

data class CartItemData(
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String? = null,
    val quantity: Int = 1
)