package com.eldar.eldarwallet.framework.datasource.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cards", indices = [Index(value = ["id"], unique = true)])
data class BankCardDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    var fullName: String,
    var cardNumber: String,
    var expDate: String,
    var securityCode: String,
    var type: String
)
