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
import com.asterisk.topupmamaassestment.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.sql.Time


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
            val userInput = "l"
            if (userInput.isEmpty()) {
                Timber.d("put in something")
            } else {
                viewModel.getSearchQuery(userInput)
                viewModel.searching = true
            }
        }
    }

//    private fun setupSearchForecastObserver() {
//        viewModel.searchedForecast.observe(viewLifecycleOwner) { result ->
//            homeAdapter.submitList(result.data)
//        }
//    }

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
        if (viewModel.searching) {
//            setupSearchForecastObserver()
            viewModel.searching = false
        } else {
            viewModel.forecast.observe(viewLifecycleOwner) { result ->
                Timber.d("this is the result ${result.data}")
                when (result.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        result.data?.let {
                            binding.rvForecast.isVisible = true
                            homeAdapter.submitList(result.data)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
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