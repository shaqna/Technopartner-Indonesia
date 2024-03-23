package com.maou.myapplicationtest.presentation.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.maou.myapplicationtest.data.source.local.AppSharedPref
import com.maou.myapplicationtest.databinding.FragmentHomeBinding
import com.maou.myapplicationtest.model.AccountInfo
import com.maou.myapplicationtest.presentation.qrcode.QRCodeActivity
import com.smarteist.autoimageslider.SliderView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private val sharedPref: AppSharedPref by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(HomeViewModel.inject())
        
        fetchAccountInfo()
        observeState()
    }

    private fun fetchAccountInfo() {
        viewModel.getAccountInfo(sharedPref.fetchToken()!!)
    }

    private fun observeState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                handleState(it)
            }.launchIn(lifecycleScope)
    }

    private fun handleState(state: HomeUiState) {
        when(state) {
            is HomeUiState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            HomeUiState.Init -> Unit
            is HomeUiState.Loading -> {

            }
            is HomeUiState.Success -> {
                setAccountInfo(state.accountInfo)
            }
        }
    }

    private fun setAccountInfo(accountInfo: AccountInfo) {
        with(binding) {
            tvWelcome.text = accountInfo.greeting
            tvName.text = accountInfo.name
            tvMoney.text = "Rp. ${accountInfo.saldo}"
            tvPointValue.text = accountInfo.point.toString()

            Glide.with(requireContext()).load(accountInfo.qrcode).into(qrCode)

            qrCode.setOnClickListener {
                Intent(requireActivity(), QRCodeActivity::class.java).also {
                    it.putExtra("qr_code", accountInfo.qrcode)
                    startActivity(it)
                }
            }

            setSliding(accountInfo.banner)
        }
    }

    private fun setSliding(listBanner: List<String>) {

        val sliderAdapter = SliderAdapter(listBanner)

        binding.sliderView.apply {
            autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
            setSliderAdapter(sliderAdapter)
            isAutoCycle = true
            scrollTimeInSec = 2
            startAutoCycle()
        }
    }


}