package com.eldar.eldarwallet.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val tagDebug = "EldarWalletDebug"
    const val dbName = "EldarWalletDB"

    const val responseError = "responseErrorCode"

    const val mastercardType = "mastercard"
    const val visaType = "visa"
    const val americanExpressType = "american_express"

    const val apiKey = "88741cb1d7msh699c9c2cb3c3712p1d879ejsn939027137e07"
    const val apiHost = "neutrinoapi-qr-code.p.rapidapi.com"

    val keyUserName = stringPreferencesKey("user_name")
    val keyUserSurname = stringPreferencesKey("user_surname")
}