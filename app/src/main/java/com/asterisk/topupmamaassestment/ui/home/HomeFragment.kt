package com.asterisk.topupmamaassestment.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.databinding.FragmentHomeBinding
import com.asterisk.topupmamaassestment.utils.Cities
import com.asterisk.topupmamaassestment.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeFragmentViewModel>()

    val cities = listOf("lagos", "abuja", "nairobi", "kano", "london")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)



        CoroutineScope(Dispatchers.Main).launch {
            for (city in Cities.listOfCities) {
                val result = async { viewModel.getQueryString(city) }
                result.await()
            }
        }

        setupPostObserver()

    }

    private fun setupPostObserver() {
        viewModel.forecast.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.tvResult.text = result.data.toString()
                }
                Status.ERROR -> {
                    binding.tvResult.text = result.message
                }
                Status.LOADING -> {

                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}