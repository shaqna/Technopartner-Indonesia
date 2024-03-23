package com.maou.myapplicationtest.presentation.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.myapplicationtest.data.repository.home.HomeRepository
import com.maou.myapplicationtest.model.AccountInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class HomeViewModel(
    private val homeRepository: HomeRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Init)
    val uiState: StateFlow<HomeUiState> = _uiState
    
    fun getAccountInfo(token: String) {
        viewModelScope.launch {
            homeRepository.getAccountInfo(token)
                .onStart {
                    _uiState.value = HomeUiState.Loading(true)
                }
                .catch {
                    _uiState.value = HomeUiState.Loading(false)
                    _uiState.value = HomeUiState.Error(it.message.toString())
                }.collect { result ->
                    _uiState.value = HomeUiState.Loading(false)
                    result.fold(
                        onFailure = {
                            _uiState.value = HomeUiState.Error(it.message.toString())
                        },
                        onSuccess = {  accountInfo ->
                            _uiState.value = HomeUiState.Success(accountInfo)
                        }
                    )

                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::HomeViewModel)
        }
    }
}

sealed interface HomeUiState {
    data object Init: HomeUiState
    data class Loading(val isLoading: Boolean): HomeUiState
    data class Error(val message: String): HomeUiState
    data class Success(val accountInfo: AccountInfo): HomeUiState
}