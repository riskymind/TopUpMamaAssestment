package com.asterisk.topupmamaassestment.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.databinding.FragmentHomeBinding
import com.asterisk.topupmamaassestment.utils.Cities
import com.asterisk.topupmamaassestment.utils.Resource
import com.asterisk.topupmamaassestment.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setUpRecyclerView()

        setupForecastObserver()

        setupSearchEvent()

    }

    private fun setupSearchEvent() {
        binding.btnRetry.setOnClickListener {
            val inputValue = ""
            if (inputValue.isEmpty()) {
                Timber.d("missing user's input")
            } else {
                viewModel.searchQuery = true
                viewModel.getSearch(inputValue)
                setupSearchForecastObserver()
            }
        }
    }

    private fun setUpRecyclerView() {
        homeAdapter = HomeAdapter()
        binding.apply {
            rvForecast.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    private fun setupForecastObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.forecast.collect {
                val result = it ?: return@collect
                binding.apply {
                    progressBar.isVisible = result.status == Status.LOADING
                    rvForecast.isVisible = !result.data.isNullOrEmpty()
                    homeAdapter.submitList(result.data)
                }
            }
        }
    }

    private fun setupSearchForecastObserver() {
        if (viewModel.searchQuery) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.searchForecast.collect {
                    val result = it ?: return@collect
                    binding.apply {
                        progressBar.isVisible = result.status == Status.LOADING
                        rvForecast.isVisible = !result.data.isNullOrEmpty()
                        homeAdapter.submitList(result.data)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}