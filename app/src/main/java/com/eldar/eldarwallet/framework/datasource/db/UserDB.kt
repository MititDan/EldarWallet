package com.eldar.eldarwallet.framework.datasource.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["id"], unique = true)])
data class UserDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    var name: String,
    var surname: String,
    var email: String,
    var password: String
)
