package com.eldar.eldarwallet.domain

data class BankCard(
    var fullName: String,
    var cardNumber: String,
    var expDate: String,
    var securityCode: String,
    var type: String
)
