package com.eldar.eldarwallet.utils

import java.net.InetAddress
import java.net.UnknownHostException

object CheckPing {
    /*fun checkPing(webPage: String): Boolean {
        MyLogger.logInfo("hilo en ping: ${Thread.currentThread().name}")

        val p = Runtime.getRuntime().exec("ping -c 1 $webPage")

        val `val` = p.waitFor()
        return `val` == 0
    }*/

    fun isInternetAvailable(webPage: String): Boolean {
        return try {
            val address = InetAddress.getByName(webPage)
            !address.equals("")
        } catch (e: UnknownHostException) {
            false
        }
    }
}