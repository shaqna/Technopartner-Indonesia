package com.maou.myapplicationtest.data.repository.home

import android.accounts.Account
import com.maou.myapplicationtest.data.source.remote.service.ApiService
import com.maou.myapplicationtest.model.AccountInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository(
    private val apiService: ApiService
){
    fun getAccountInfo(token: String): Flow<Result<AccountInfo>> =
        flow {
            val response = apiService.getAccountData(token)
            if(response.status != "success") {
                emit(Result.failure(Exception(response.status)))
            }

            emit(Result.success(response.result))
        }.flowOn(Dispatchers.IO)
}
