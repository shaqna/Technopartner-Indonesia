package com.maou.myapplicationtest.data.repository.auth

import com.maou.myapplicationtest.data.source.local.AppSharedPref
import com.maou.myapplicationtest.data.source.remote.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody

class AuthRepository(
    private val apiService: ApiService,
    private val sharedPref: AppSharedPref
) {
    fun login(username: RequestBody, password: RequestBody): Flow<Result<String>> {
        return flow {
            val response = apiService.login(username = username, password = password)

            if(response.error != null) {
                emit(Result.failure(Exception(response.error)))
            }
            sharedPref.saveToken(response.accessToken!!)
            emit(Result.success(response.accessToken))
        }.flowOn(Dispatchers.IO)
    }

}