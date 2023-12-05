package com.eldar.eldarwallet.framework.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(value: UserDB): Long
}