package com.example.cryptoapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptoapp.R
import com.example.cryptoapp.adapter.CryptoAdapter
import com.example.cryptoapp.adapter.FavoritesAdapter
import com.example.cryptoapp.databinding.FragmentFavouritesBinding
import com.example.cryptoapp.room.CoinDatabase
import com.example.cryptoapp.util.viewBinding
import com.example.cryptoapp.viewmodel.CryptoViewModel
import com.example.cryptoapp.viewmodel.DetailsViewModel
import com.example.cryptoapp.viewmodel.FavouritesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val binding by viewBinding(FragmentFavouritesBinding::bind)
    private val viewModel: FavouritesViewModel by viewModels()
    private lateinit var adapter : FavoritesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavoritesAdapter(requireContext(),viewModel)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            viewModel.getAll()
            binding.cryptoError.visibility = View.GONE
            binding.cryptoLoading.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.getAll()
        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.coinList.observe(viewLifecycleOwner){list->
            list?.let {
                adapter.submitList(it)
                binding.recyclerView.adapter = adapter
                binding.recyclerView.visibility = View.VISIBLE
                binding.cryptoLoading.visibility = View.GONE
            }
        }

    }


}