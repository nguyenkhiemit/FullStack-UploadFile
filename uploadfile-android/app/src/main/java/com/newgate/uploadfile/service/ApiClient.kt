package com.newgate.uploadfile.service

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by apple on 2/21/18.
 */
class ApiClient {

    val BASE_URL = "http://192.168.1.5:3000/"

    companion object {
        var clientInstall: ApiClient? = null
        fun getInstance(): ApiClient {
            if (clientInstall == null) {
                clientInstall = ApiClient()
            }
            return clientInstall!!
        }
    }

    fun getClient() : Retrofit {
        var retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getRequestHeader())
                .build()
        return retrofit
    }

    fun getRequestHeader(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
        return okHttpClient.build()
    }
}