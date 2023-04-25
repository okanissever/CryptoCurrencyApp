package com.example.cryptoapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.room.CoinDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {
    private val coinDao = CoinDatabase(getApplication()).coinDao()
     var coinList = MutableLiveData<List<CryptoItem>>()

    fun getAll(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = coinDao.getAll()
            withContext(Dispatchers.Main) {
                coinList.value = list
            }
        }
    }

    fun deleteCoin(coin: CryptoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            coinDao.deleteCoin(coin)
            getAll()
        }
    }

}