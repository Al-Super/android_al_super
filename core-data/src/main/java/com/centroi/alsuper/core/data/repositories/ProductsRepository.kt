package com.centroi.alsuper.core.data.repositories

import com.centroi.alsuper.core.database.tables.Product
import com.centroi.alsuper.core.database.tables.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ProductsRepository {
    fun getProducts(): Flow<List<Product>>
    suspend fun insertData(products: List<Product>)
}

class ProductsRepositoryImpl @Inject constructor(
    private val dao: ProductDao
) : ProductsRepository {

    override fun getProducts(): Flow<List<Product>> = dao.getAllProducts()
        .map { it.map { entity -> entity } }

    override suspend fun insertData(products: List<Product>) {
        dao.clearProducts()
        dao.insertProducts(products.map { it })
    }
}