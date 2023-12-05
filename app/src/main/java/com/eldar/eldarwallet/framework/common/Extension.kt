package com.eldar.eldarwallet.framework.common

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

fun checkValidEmail(email: String): Boolean {
    return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
}

fun checkValidPassword(password: String): Boolean {
    val passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+"
    val pattern = Pattern.compile(passwordPattern)
    return pattern.matcher(password).matches()
}

fun getFirstNumber(text: String): Int? {
    for (char in text) {
        if (char.isDigit()) {
            return char.toString().toInt()
        }
    }
    return null
}

fun getLastFourNumber(text: String): String {
    return if (text.length >= 4) {
        text.substring(text.length - 4)
    } else {
        text
    }
}