package com.maou.myapplicationtest.presentation.dashboard.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maou.myapplicationtest.data.repository.menu.MenuRepository
import com.maou.myapplicationtest.model.AccountInfo
import com.maou.myapplicationtest.model.CategoryMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import java.util.Locale.Category

class MenuViewModel(
    private val menuRepository: MenuRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<MenuUiState> = MutableStateFlow(
        MenuUiState.Init)
    val uiState: StateFlow<MenuUiState> = _uiState


    fun getMenu(token: String) {
        viewModelScope.launch {
            menuRepository.getMenu(token)
                .onStart {
                    _uiState.value = MenuUiState.Loading(true)
                }
                .catch {
                    _uiState.value = MenuUiState.Loading(false)
                    _uiState.value = MenuUiState.Error(it.message.toString())
                }.collect { result ->
                    _uiState.value = MenuUiState.Loading(false)
                    result.fold(
                        onFailure = {
                            _uiState.value = MenuUiState.Error(it.message.toString())
                        },
                        onSuccess = {  listMenu ->
                            _uiState.value = MenuUiState.Success(listMenu)
                        }
                    )

                }
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::MenuViewModel)
        }
    }
}

sealed interface MenuUiState {
    data object Init: MenuUiState
    data class Loading(val isLoading: Boolean): MenuUiState
    data class Error(val message: String): MenuUiState
    data class Success(val listMenu: List<CategoryMenu>): MenuUiState
}