package com.eldar.eldarwallet.data.datasource

import com.eldar.eldarwallet.domain.BankCard

interface CardsData {
    suspend fun insertNewCard(cardItem: BankCard): Long?
    suspend fun getCards() : List<BankCard>
}