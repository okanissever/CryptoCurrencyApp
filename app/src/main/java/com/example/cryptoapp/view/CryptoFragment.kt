package com.example.cryptoapp.view

import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.R
import com.example.cryptoapp.adapter.CryptoAdapter
import com.example.cryptoapp.databinding.FragmentCryptoBinding
import com.example.cryptoapp.util.viewBinding
import com.example.cryptoapp.viewmodel.CryptoViewModel


class CryptoFragment : Fragment(R.layout.fragment_crypto) {
    private val binding by viewBinding(FragmentCryptoBinding::bind)
    private val viewModel: CryptoViewModel by viewModels()
    private val adapter = CryptoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.cryptoRecyclerView.visibility = View.GONE
            viewModel.downloadData()
            binding.cryptoError.visibility = View.GONE
            binding.cryptoLoading.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.downloadData()
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.coinList.observe(viewLifecycleOwner){coins->
            coins?.let {
                binding.cryptoRecyclerView.visibility = View.VISIBLE
                adapter.submitList(it)
                binding.cryptoRecyclerView.adapter = adapter
                adapter.onCoinClick = {
                    val action = CryptoFragmentDirections.actionCryptoFragmentToDetailsFragment(it)
                    findNavController().navigate(action)
                }
            }
        }
        viewModel.coinError.observe(viewLifecycleOwner){error->
            error?.let {
                if(it){
                    binding.cryptoError.visibility = View.VISIBLE
                    binding.cryptoLoading.visibility = View.GONE
                    binding.cryptoRecyclerView.visibility = View.GONE
                }
                else{
                    binding.cryptoError.visibility = View.GONE
                }
            }
        }
        viewModel.coinLoading.observe(viewLifecycleOwner){loading->
            loading?.let {
                if(it){
                    binding.cryptoLoading.visibility = View.VISIBLE
                    binding.cryptoError.visibility = View.GONE
                    binding.cryptoRecyclerView.visibility = View.GONE
                }
                else{
                    binding.cryptoLoading.visibility = View.GONE
                }
            }

        }
    }


}