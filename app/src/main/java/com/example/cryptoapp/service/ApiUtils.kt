package com.example.cryptoapp.service

import com.example.cryptoapp.util.Constans.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
        .build()

    val CoinApiService by lazy { retrofit.create(CoinApiService::class.java) }

}