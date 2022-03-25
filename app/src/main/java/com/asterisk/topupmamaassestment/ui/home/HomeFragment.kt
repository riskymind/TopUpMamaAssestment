package com.asterisk.topupmamaassestment.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.asterisk.topupmamaassestment.R
import com.asterisk.topupmamaassestment.data.models.local.ForecastResponse
import com.asterisk.topupmamaassestment.databinding.FragmentHomeBinding
import com.asterisk.topupmamaassestment.utils.onQueryTextChanger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeFragmentViewModel>()

    private lateinit var homeAdapter: HomeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        homeAdapter = HomeAdapter(
            onItemClicked = {
                viewModel.onItemClicked(it)
            },
            onFavClicked = {
                viewModel.onFavClick(it)
            }
        )

        setHasOptionsMenu(true)

        setUpRecyclerView()

        setupForecastObserver()

        setupHomeEvents()

    }


    private fun setUpRecyclerView() {
        binding.apply {
            rvForecast.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }
    }

    private fun setupForecastObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchFlow.collect {
                binding.apply {
                    progressBar.isVisible = it.isEmpty()
                    btnRetry.isVisible = it.isEmpty()
                }
                homeAdapter.submitList(it)
            }
        }
    }

    private fun setupHomeEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeEvent.collect { event ->
                when (event) {
                    is HomeFragmentViewModel.ForecastEvent.NavigateToDetailScreen -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.forecast_menu, menu)

        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView
        searchView.onQueryTextChanger {
            viewModel.searchQuery.value = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}