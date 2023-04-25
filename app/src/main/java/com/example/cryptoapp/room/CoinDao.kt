package com.example.cryptoapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptoapp.model.CryptoItem

@Dao
interface CoinDao {

    @Insert
    suspend fun addCoin(coin : CryptoItem)

    @Delete
    suspend fun deleteCoin(coin : CryptoItem)

    @Query("SELECT * FROM crypto_table")
    suspend fun getAll() : List<CryptoItem>

    @Query("SELECT * FROM crypto_table WHERE id = :id")
    suspend fun getCoinById(id: String): CryptoItem?

}