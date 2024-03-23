package com.maou.myapplicationtest.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.maou.myapplicationtest.R
import com.maou.myapplicationtest.databinding.ActivityAuthBinding
import com.maou.myapplicationtest.presentation.dashboard.DashboardActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class AuthActivity : AppCompatActivity() {

    private val binding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(AuthViewModel.inject())

        login()
        observeState()
    }

    private fun login() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val username = edtEmail.editText?.text.toString().toRequestBody("text/plain".toMediaType())
                val password = edtPassword.editText?.text.toString().toRequestBody("text/plain".toMediaType())

                viewModel.login(username, password)
            }

        }
    }

    private fun observeState() {
        viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
            .onEach {
                handleState(it)
            }.launchIn(lifecycleScope)
    }

    private fun handleState(state: AuthUiState) {
        when(state) {
            is AuthUiState.Error -> {
                Toast.makeText(this@AuthActivity, state.message, Toast.LENGTH_SHORT).show()
            }
            AuthUiState.Init -> Unit
            is AuthUiState.Loading -> {
                Toast.makeText(this@AuthActivity, "Loading...", Toast.LENGTH_SHORT).show()
            }
            is AuthUiState.Success -> {
                Intent(this@AuthActivity, DashboardActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }
    }
}