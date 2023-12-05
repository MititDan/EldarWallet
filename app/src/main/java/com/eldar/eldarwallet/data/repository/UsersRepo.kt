package com.eldar.eldarwallet.data.repository

import com.eldar.eldarwallet.data.datasource.UsersData
import com.eldar.eldarwallet.domain.User
import javax.inject.Inject

class UsersRepo @Inject constructor(
    private val data: UsersData
) {

    suspend fun saveNewUser(user: User) : Long {
        return data.insertNewUser(user)
    }

    suspend fun loginUser(email: String, password: String) : Boolean {
        return data.login(email, password)
    }
}