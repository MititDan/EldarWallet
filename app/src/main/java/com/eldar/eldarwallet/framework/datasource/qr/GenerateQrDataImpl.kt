package com.eldar.eldarwallet.framework.datasource.qr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.eldar.eldarwallet.data.datasource.GenerateQrData
import com.eldar.eldarwallet.utils.CheckPing
import com.eldar.eldarwallet.utils.Constants
import com.eldar.eldarwallet.utils.GenericHttpConnection
import com.eldar.eldarwallet.utils.MyLogger
import com.eldar.eldarwallet.utils.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class GenerateQrDataImpl @Inject constructor(
    private val url: String
) : GenerateQrData {

    override suspend fun queryGenerateQr(
        fullName: String
    ): Bitmap? = suspendCancellableCoroutine { continuation ->
        try {
            val retrofit = RetrofitClient.getRetrofit(url)

            val service = retrofit.create(GenerateQrService::class.java)
            val call = service.operation(
                Constants.apiKey, Constants.apiHost, fullName
            )

            GenericHttpConnection.makeApiCall(call,
                onResponse = { response ->
                    response?.let {
                        MyLogger.logInfo("respuesta exitosa al generar qr")

                        val imageBytes: ByteArray = response.bytes()

                        val bitmap =
                            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                        bitmap?.let {
                            continuation.resume(bitmap)

                        } ?: run {
                            continuation.resume(null)
                        }

                    } ?: run {
                        MyLogger.logError("respuesta null al generar qr")
                        continuation.resume(null)
                    }
                },
                onFailure = { error ->
                    if (error.contains(Constants.responseError)) {
                        MyLogger.logError("error en query qr: $error")
                        continuation.resume(null)

                    } else {
                        MyLogger.logError("error en query qr: $error")
                        continuation.resume(null)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            MyLogger.logError("catch queryGenerateQr: ${e.message}")
            continuation.resume(null)
        }
    }
}