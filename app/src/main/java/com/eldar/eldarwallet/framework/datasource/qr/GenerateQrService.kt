package com.eldar.eldarwallet.framework.datasource.qr

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface GenerateQrService {
    @FormUrlEncoded
    @POST("/qr-code")
    fun operation(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Header("X-RapidAPI-Host") apiHost: String,
        @Field("content") content: String
    ): Call<ResponseBody>
}