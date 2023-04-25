package com.example.cryptoapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.FragmentDetailsBinding
import com.example.cryptoapp.model.CryptoItem
import com.example.cryptoapp.room.CoinDatabase
import com.example.cryptoapp.util.viewBinding
import com.example.cryptoapp.viewmodel.CryptoViewModel
import com.example.cryptoapp.viewmodel.DetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val args : DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.coin.let {coin->
            binding.apply {
                tvCoinTitle.text = coin.name
                tvAth.text = "${coin.ath.toString()}$"
                tvCurrentPrice.text = "${coin.currentPrice.toString()}$"
                tvCirculatingSupply.text = coin.circulatingSupply?.toLong()?.toString() ?: "N/A"
                tvMarketCapRank.text = coin.marketCapRank.toString()
                tvTotalVolume.text = coin.totalVolume?.toLong()?.toString() ?: "N/A"
                tvHigh24h.text = "${coin.high24h.toString()}$"
                tvLow24h.text = "${coin.low24h.toString()}$"
                tvMaxSupply.text = coin.maxSupply?.toLong()?.toString() ?: "N/A"
                Glide.with(binding.imgCoin).load(coin.image).into(binding.imgCoin)
                Glide.with(binding.imgCoinBack).load(coin.image).into(binding.imgCoinBack)
            }
            binding.addToFavorites.setOnClickListener {
                viewModel.insertCoin(coin)
            }
        }
    }



}