package com.eldar.eldarwallet.framework.datasource.cards

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.eldar.eldarwallet.data.datasource.CardsData
import com.eldar.eldarwallet.domain.BankCard
import com.eldar.eldarwallet.framework.datasource.db.BankCardDB
import com.eldar.eldarwallet.framework.datasource.db.AppDataBase
import com.eldar.eldarwallet.utils.AesEncryption
import com.eldar.eldarwallet.utils.Constants
import com.eldar.eldarwallet.utils.DatastoreManager
import com.eldar.eldarwallet.utils.MyLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

class CardsDataImpl @Inject constructor(
    private val context: Context,
    private val db: AppDataBase
) : CardsData {

    override suspend fun insertNewCard(cardItem: BankCard): Long? =
        withContext(Dispatchers.IO) {
            try {
                val userName = DatastoreManager.getData(
                    context, Constants.keyUserName, ""
                )
                val userSurname = DatastoreManager.getData(
                    context, Constants.keyUserSurname, ""
                )
                val fullName = cardItem.fullName.lowercase(Locale.ROOT)

                //verifico que el nombre y apellido este en el nombre completo de la tarjeta
                if (userName != null && fullName.contains(userName.lowercase(Locale.ROOT)) &&
                    userSurname != null && fullName.contains(userSurname.lowercase(Locale.ROOT))) {

                    AesEncryption.getKey()?.let { aesKey ->
                        val encryptedModel = BankCardDB(
                            fullName = AesEncryption.encrypt(cardItem.fullName, aesKey),
                            cardNumber = AesEncryption.encrypt(cardItem.cardNumber, aesKey),
                            expDate = AesEncryption.encrypt(cardItem.expDate, aesKey),
                            securityCode = AesEncryption.encrypt(cardItem.securityCode, aesKey),
                            type = AesEncryption.encrypt(cardItem.type, aesKey)
                        )

                        return@withContext db.cardsDao().insertCard(encryptedModel)

                    } ?: run {
                        return@withContext 0L
                    }
                } else {
                    //la tarjeta no pertenece al usuario
                    Log.e(Constants.tagDebug, "la tarjeta no pertenece al usuario actual")
                    return@withContext null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MyLogger.logError("catch insertNewCard: ${e.message}")
                return@withContext 0L
            }
        }

    override suspend fun getCards() : List<BankCard> = withContext(Dispatchers.IO) {
        try {
            AesEncryption.getKey()?.let { aesKey ->
                val allCards = db.cardsDao().getAllCards()

                if (allCards.isNotEmpty()) {
                    MyLogger.logInfo("encrypted list: $allCards")

                    val userName = DatastoreManager.getData(
                        context, Constants.keyUserName, ""
                    )
                    val userSurname = DatastoreManager.getData(
                        context, Constants.keyUserSurname, ""
                    )

                    val decryptedList = mutableListOf<BankCard>()
                    allCards.map { item ->
                        val fullName = AesEncryption.decrypt(item.fullName, aesKey).lowercase(Locale.ROOT)

                        Log.i(Constants.tagDebug, "usuario: $userName $userName. tarjeta: $fullName")

                        //verifico que el nombre y apellido este en el nombre completo de la tarjeta
                        if (userName != null && fullName.contains(userName.lowercase(Locale.ROOT)) &&
                            userSurname != null && fullName.contains(userSurname.lowercase(Locale.ROOT))) {

                            val decryptedModel = BankCard(
                                fullName = AesEncryption.decrypt(item.fullName, aesKey),
                                cardNumber = AesEncryption.decrypt(item.cardNumber, aesKey),
                                expDate = AesEncryption.decrypt(item.expDate, aesKey),
                                securityCode = AesEncryption.decrypt(item.securityCode, aesKey),
                                type = AesEncryption.decrypt(item.type, aesKey)
                            )

                            decryptedList.add(decryptedModel)
                        }
                    }

                    MyLogger.logInfo("decryptedList: $decryptedList")
                    return@withContext decryptedList

                } else {
                    Log.e(Constants.tagDebug, "db local vacia")
                    return@withContext listOf()
                }

            } ?: run {
                return@withContext listOf()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch getCards: ${e.message}")
            return@withContext listOf()
        }
    }
}