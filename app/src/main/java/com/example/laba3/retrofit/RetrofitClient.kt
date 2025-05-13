package com.example.laba3.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.laba3.BuildConfig

object RetrofitClient {

    private const val BASE_URL = "https://api.api-ninjas.com/"

    private val apiKey = BuildConfig.API_NINJAS_KEY

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("X-Api-Key", apiKey)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    val apiService: DictionaryApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApiService::class.java)
    }
}