package com.eldar.eldarwallet.framework.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardsDao {
    @Query("SELECT * FROM cards")
    fun getAllCards(): List<BankCardDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCard(value: BankCardDB): Long
}