package com.maou.myapplicationtest.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.myapplicationtest.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.Init)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun login(username: RequestBody, password: RequestBody) {
        viewModelScope.launch {
            authRepository.login(username, password)
                .onStart {
                    _uiState.value = AuthUiState.Loading(true)
                }
                .catch {
                    _uiState.value = AuthUiState.Loading(false)
                    _uiState.value = AuthUiState.Error(it.message.toString())
                }.collect { result ->
                    _uiState.value = AuthUiState.Loading(false)
                    result.fold(
                        onFailure = {
                            _uiState.value = AuthUiState.Error(it.message.toString())
                        },
                        onSuccess = { token ->
                            _uiState.value = AuthUiState.Success(token)
                        }
                    )

                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::AuthViewModel)
        }
    }
}

sealed interface AuthUiState {
    data object Init: AuthUiState
    data class Loading(val isLoading: Boolean): AuthUiState
    data class Error(val message: String): AuthUiState
    data class Success(val token: String): AuthUiState
}