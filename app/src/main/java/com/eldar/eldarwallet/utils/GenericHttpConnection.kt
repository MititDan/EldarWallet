package com.eldar.eldarwallet.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GenericHttpConnection {
    fun <T> makeApiCall(
        call: Call<T>,
        onResponse: (T?) -> Unit,
        onFailure: (String) -> Unit) {

        try {
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        onResponse(body)
                    } else {
                        onFailure("${Constants.responseError}, code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    onFailure("onFailure: ${t.message}")
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            onFailure("catch: ${e.message}")
        }
    }
}