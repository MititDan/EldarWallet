package com.eldar.eldarwallet.data.repository

import android.graphics.Bitmap
import com.eldar.eldarwallet.data.datasource.GenerateQrData
import javax.inject.Inject

class GenerateQrRepo @Inject constructor(
    private val data: GenerateQrData
) {

    suspend fun generateQr(fullName: String) : Bitmap? {
        return data.queryGenerateQr(fullName)
    }
}