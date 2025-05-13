package com.example.laba3.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface DictionaryApiService {

    @GET("v1/dictionary")
    suspend fun getDefinition(
        @Query("word") word: String
    ): Response<DictionaryResponse>
}