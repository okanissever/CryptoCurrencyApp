package com.example.cryptoapp.viewmodel

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.room.CoinDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val coinDao = CoinDatabase(getApplication()).coinDao()

    fun insertCoin(coin: CryptoItem) {
        viewModelScope.launch {
            val existingCoin = coinDao.getCoinById(coin.id.toString())
            if (existingCoin == null) {
                coinDao.addCoin(coin)
            }
        }
    }


}