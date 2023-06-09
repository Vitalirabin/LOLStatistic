package com.example.lolstatistic.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object ApiFactory {
    private val BASE_URL = "https://ru.api.riotgames.com"
    private var retrofit: Retrofit? = null

    fun getApi(): RemoteApi {
        return getClient(BASE_URL).create<RemoteApi>()
    }

    fun okhttp(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("okhttp", message) })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return okClient
    }

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp())
                .build()
        }
        return retrofit!!
    }
}
