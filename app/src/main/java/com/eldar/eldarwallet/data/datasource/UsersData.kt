package com.eldar.eldarwallet.data.datasource

import com.eldar.eldarwallet.domain.User

interface UsersData {
    suspend fun insertNewUser(user: User): Long
    suspend fun login(
        email: String, password: String
    ): Boolean
}