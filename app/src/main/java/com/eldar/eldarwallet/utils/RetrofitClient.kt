package com.eldar.eldarwallet.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder().baseUrl(
            url
        )
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            ).build()
    }
}