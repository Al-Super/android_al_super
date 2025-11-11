package com.centroi.alsuper.core.database.tables

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: String,
    val name: String,
    val price: Double,
    val imageUrl: String? = null,
    val quantity: Int = 1
)

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Query("SELECT * FROM CartItem")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Query("DELETE FROM CartItem WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: String)
}