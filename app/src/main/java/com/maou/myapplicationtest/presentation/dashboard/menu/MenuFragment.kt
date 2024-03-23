package com.maou.myapplicationtest.presentation.dashboard.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.State
import com.maou.myapplicationtest.data.source.local.AppSharedPref
import com.maou.myapplicationtest.databinding.FragmentMenuBinding
import com.maou.myapplicationtest.model.CategoryMenu
import com.maou.myapplicationtest.model.Dish
import com.maou.myapplicationtest.presentation.dashboard.menu.adapter.CategoryAdapter
import com.maou.myapplicationtest.presentation.dashboard.menu.adapter.MenuAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: MenuViewModel by viewModel()
    private val sharedPref: AppSharedPref by inject()

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter().apply {
            setOnItemCallback { category , pos ->
                val linearLayoutManager = binding.rvMenu.layoutManager as LinearLayoutManager
                linearLayoutManager.scrollToPositionWithOffset(pos, 0)
            }
        }
    }

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(MenuViewModel.inject())
        setupAdapter()
        fetchMenu()
        observeState()
    }

    private fun setupAdapter() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        binding.rvMenu.apply {
            adapter = menuAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }


    }
    
    private fun fetchMenu() {
        viewModel.getMenu(sharedPref.fetchToken()!!)
    }

    private fun observeState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                handleState(it)
            }.launchIn(lifecycleScope)
    }

    private fun handleState(state: MenuUiState) {
        when(state) {
            is MenuUiState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            MenuUiState.Init -> Unit
            is MenuUiState.Loading -> {

            }
            is MenuUiState.Success -> {
                val categoryList = state.listMenu.map {
                    it.categoryName
                }

                setCategory(categoryList)
                setMenu(state.listMenu)
            }
        }
    }

    private fun setMenu(listMenu: List<CategoryMenu>) {
        menuAdapter.setList(listMenu)
    }

    private fun setCategory(categoryList: List<String>) {
        categoryAdapter.setList(categoryList)
    }

}