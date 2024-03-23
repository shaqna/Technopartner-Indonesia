package com.maou.myapplicationtest.data.source.remote.service

import com.maou.myapplicationtest.data.source.remote.response.AccountResponse
import com.maou.myapplicationtest.data.source.remote.response.AuthResponse
import com.maou.myapplicationtest.data.source.remote.response.MenuResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("oauth/token")
    suspend fun login(
        @Part("grant_type") grantType: RequestBody? = "password".toRequestBody("text/plain".toMediaType()),
        @Part("client_secret") clientSecret: RequestBody? = "0a40f69db4e5fd2f4ac65a090f31b823".toRequestBody(
            "text/plain".toMediaType()
        ),
        @Part("client_id") clientId: RequestBody? = "e78869f77986684a".toRequestBody("text/plain".toMediaType()),
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody
    ): AuthResponse

    @GET("api/home")
    suspend fun getAccountData(
        @Header("Authorization") token: String
    ): AccountResponse

    @Multipart
    @POST("api/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String,
        @Part("show_all") showAll: RequestBody = "1".toRequestBody("text/plain".toMediaType())
    ): MenuResponse

}