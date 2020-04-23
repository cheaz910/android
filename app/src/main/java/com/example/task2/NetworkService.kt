package com.example.task2

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    val jSONApi: HabitApi
        get() = mRetrofit.create(HabitApi::class.java)

    companion object {
        private var mInstance: NetworkService? = null
        private const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"
        private const val TOKEN = "83b714ae-a87c-4335-8636-7f804fe01e88"
        val instance: NetworkService
            get() {
                if (mInstance == null) {
                    mInstance = NetworkService()
                }
                return mInstance!!
            }
    }

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request: Request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", TOKEN)
                .build()
            chain.proceed(request)
        }
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}