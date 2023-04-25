package com.example.cryptoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.service.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoViewModel : ViewModel() {
    val service = ApiUtils.CoinApiService
    val coinList = MutableLiveData<List<CryptoItem>>()
    val coinError = MutableLiveData<Boolean>()
    val coinLoading = MutableLiveData<Boolean>()
    var job : Job? = null


    fun downloadData(){
        job = viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main){
                coinLoading.value = true
            }
            try {
                val response = service.getAll()
                withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        coinList.value = it
                    }
                }else{
                    coinError.value = true
                    println("Error code: ${response.code()}")
                    println("Error message: ${response.message()}")
                }
                    coinLoading.value = false
                }
            }
            catch (e:java.lang.Exception){
                e.printStackTrace()
                println("Exception type: ${e::class.simpleName}")
                println("Exception message: ${e.message}")
                coinError.postValue(true)
                coinLoading.postValue(false)
            }

        }

    }



}