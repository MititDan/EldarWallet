package com.eldar.eldarwallet.data.repository

import com.eldar.eldarwallet.data.datasource.CardsData
import com.eldar.eldarwallet.domain.BankCard
import javax.inject.Inject

class CardsRepo @Inject constructor(
    private val data: CardsData
) {

    suspend fun saveNewCard(card: BankCard) : Long? {
        return data.insertNewCard(card)
    }

    suspend fun getAllCards() : List<BankCard> {
        return data.getCards()
    }
}