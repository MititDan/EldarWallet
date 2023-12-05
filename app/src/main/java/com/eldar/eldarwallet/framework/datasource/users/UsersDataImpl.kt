package com.eldar.eldarwallet.framework.datasource.users

import android.content.Context
import android.util.Log
import com.eldar.eldarwallet.data.datasource.UsersData
import com.eldar.eldarwallet.domain.User
import com.eldar.eldarwallet.framework.datasource.db.AppDataBase
import com.eldar.eldarwallet.framework.datasource.db.UserDB
import com.eldar.eldarwallet.utils.AesEncryption
import com.eldar.eldarwallet.utils.Constants
import com.eldar.eldarwallet.utils.DatastoreManager
import com.eldar.eldarwallet.utils.MyLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersDataImpl @Inject constructor(
    private val context: Context,
    private val db: AppDataBase
) : UsersData {

    override suspend fun insertNewUser(user: User): Long =
        withContext(Dispatchers.IO) {
            try {
                AesEncryption.getKey()?.let { aesKey ->
                    val encryptedModel = UserDB(
                        name = AesEncryption.encrypt(user.name, aesKey),
                        surname = AesEncryption.encrypt(user.surname, aesKey),
                        email = AesEncryption.encrypt(user.email, aesKey),
                        password = AesEncryption.encrypt(user.password, aesKey)
                    )

                    return@withContext db.usersDao().insertUser(encryptedModel)

                } ?: run {
                    return@withContext 0L
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MyLogger.logError("catch insertNewUser: ${e.message}")
                return@withContext 0L
            }
        }

    private suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        try {
            AesEncryption.getKey()?.let { aesKey ->
                val allUsers = db.usersDao().getAllUsers()

                if (allUsers.isNotEmpty()) {
                    MyLogger.logInfo("encrypted list: $allUsers")

                    val decryptedList = mutableListOf<User>()
                    allUsers.map { item ->
                        val decryptedModel = User(
                            name = AesEncryption.decrypt(item.name, aesKey),
                            surname = AesEncryption.decrypt(item.surname, aesKey),
                            email = AesEncryption.decrypt(item.email, aesKey),
                            password = AesEncryption.decrypt(item.password, aesKey)
                        )

                        decryptedList.add(decryptedModel)
                    }

                    MyLogger.logInfo("decryptedList: $decryptedList")
                    return@withContext decryptedList

                } else {
                    return@withContext listOf()
                }

            } ?: run {
                return@withContext listOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch getUsers: ${e.message}")
            return@withContext listOf()
        }
    }

    override suspend fun login(
        email: String, password: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val users = getUsers()

            var loginState = false
            var name = ""
            var surname = ""

            //verifico si el usuario existe y la contrasena es correcta
            users.map { item ->
                if (email == item.email && password == item.password) {
                    name = item.name
                    surname = item.surname
                    loginState = true
                }
            }

            if (loginState) {
                //guardo nombre y apellido luego del login para reutilizarlo(se borran al cerrar sesion)
                DatastoreManager.saveData(context, Constants.keyUserName, name)
                DatastoreManager.saveData(context, Constants.keyUserSurname, surname)

                return@withContext true

            } else {
                return@withContext false
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(Constants.tagDebug, "")
            return@withContext false
        }
    }
}