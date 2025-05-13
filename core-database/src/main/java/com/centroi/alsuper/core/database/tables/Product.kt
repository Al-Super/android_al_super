package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Product(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val productCategoryId: Long,
    val isFavorite: Boolean = false
)

enum class ProductCategory(val id: Long, val displayName: String) {
    Electronics(1, "Electronics"),
    Clothing(2, "Clothing"),
    HomeAppliances(3, "Home Appliances");

    companion object {
        fun fromId(id: Long): ProductCategory? = entries.firstOrNull { it.id == id }
    }
}

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("DELETE FROM product")
    suspend fun clearProducts()
}
