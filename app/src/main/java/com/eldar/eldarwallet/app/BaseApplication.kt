package com.eldar.eldarwallet.app

import android.app.Application
import com.eldar.eldarwallet.utils.AesEncryption
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            AesEncryption.generateKeyIfNotExists()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}