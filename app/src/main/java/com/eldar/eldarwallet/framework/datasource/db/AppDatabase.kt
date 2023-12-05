package com.eldar.eldarwallet.framework.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserDB::class,
        BankCardDB::class
    ],
    version = 1, exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun usersDao() : UsersDao
    abstract fun cardsDao() : CardsDao
}