package com.example.cryptoapp.service

import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.util.Constans.EXT_URL
import retrofit2.Response
import retrofit2.http.GET

interface CoinApiService {

    @GET(EXT_URL)
    suspend fun getAll() : Response<List<CryptoItem>>
}