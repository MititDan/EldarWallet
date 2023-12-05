package com.eldar.eldarwallet.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    val baseRoute: String,
    val navArgs: List<NavArg> = emptyList()
) {
    val route = run {
        val argKeys = navArgs.map {
            "{${it.key}}"
        }

        listOf(baseRoute).plus(argKeys).joinToString("/")
    }

    val args = navArgs.map { item ->
        navArgument(item.key) { type = item.navType }
    }

    object Login : NavItem("login")
    object CreateUser : NavItem("create_user")
    object Home : NavItem("home")
    object NewBankCard: NavItem("new_bank_card")
    object Qr: NavItem("qr")

    object Payment : NavItem(baseRoute = "payment", navArgs = listOf(
        NavArg.Type, NavArg.CardNumber
    )) {
        fun createNavRoute(
            type: String, cardNumber: String
        ) = "$baseRoute/$type/$cardNumber"
    }

    object PaymentCompleted: NavItem("payment_completed")
}

enum class NavArg(val key: String, val navType: NavType<*>) {
    Type(key = "type", navType = NavType.StringType),
    CardNumber(key = "card_number", navType = NavType.StringType)
}