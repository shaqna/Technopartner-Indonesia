package com.maou.myapplicationtest.data.repository.menu

import com.maou.myapplicationtest.data.source.remote.service.ApiService
import com.maou.myapplicationtest.model.CategoryMenu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuRepository(
    private val apiService: ApiService
) {

    fun getMenu(token: String): Flow<Result<List<CategoryMenu>>> =
        flow {
            val response = apiService.getMenu(token)
            if(response.status != "success") {
                emit(Result.failure(Exception(response.status)))
            }
            emit(Result.success(response.result.categories))
        }.flowOn(Dispatchers.IO)
}