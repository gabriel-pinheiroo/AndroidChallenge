package com.maximatech.provaandroid.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.maximatech.provaandroid.core.data.local.entity.OrderEntity

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Query("SELECT * FROM orders ORDER BY data DESC")
    suspend fun getAllOrders(): List<OrderEntity>

    @Query("DELETE FROM orders")
    suspend fun deleteAllOrders()

    @Transaction
    suspend fun saveOrders(orders: List<OrderEntity>) {
        deleteAllOrders()
        insertOrders(orders)
    }
}