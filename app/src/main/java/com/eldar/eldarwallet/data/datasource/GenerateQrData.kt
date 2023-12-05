package com.eldar.eldarwallet.data.datasource

import android.graphics.Bitmap

interface GenerateQrData {
    suspend fun queryGenerateQr(
        fullName: String
    ): Bitmap?
}