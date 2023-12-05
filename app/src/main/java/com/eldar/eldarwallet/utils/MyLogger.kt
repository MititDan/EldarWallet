package com.eldar.eldarwallet.utils

import android.util.Log
import com.eldar.eldarwallet.utils.Constants.tagDebug

//para imprimir logs sin afectar los test unitarios
object MyLogger {
    fun logInfo(message: String) {
        if (!isRunningUnitTest()) {
            Log.i(tagDebug, message)
        }
    }

    fun logError(message: String) {
        if (!isRunningUnitTest()) {
            Log.e(tagDebug, message)
        }
    }

    private fun isRunningUnitTest(): Boolean {
        val testFramework = System.getProperty("java.class.path")
        testFramework?.let {
            return testFramework.contains("junit") || testFramework.contains("kotest")
        } ?: run {
            return false
        }
    }
}