package com.mills.justin.republicserviceschallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mills.justin.republicserviceschallenge.R
import com.mills.justin.republicserviceschallenge.databinding.FragmentDriverListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DriverListFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDriverListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DriverListViewModel by viewModels()

    private val driverListAdapter: DriverListAdapter by lazy {
        DriverListAdapter(this::onClickDriver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDriverListBinding.inflate(inflater, container, false)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.recyclerView.adapter = driverListAdapter
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    driverListAdapter.submitList(it.drivers)
                }
            }
        }
    }

    private fun onClickDriver(id: String) {
        findNavController().navigate(DriverListFragmentDirections.toRouteList(id))
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)
        // TODO handle tinting the icon in light/night themes
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sort_by_alpha -> {
                viewModel.onClickSort()
                true
            }
            else -> false
        }
    }
}