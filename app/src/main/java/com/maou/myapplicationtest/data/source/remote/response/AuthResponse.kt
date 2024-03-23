package com.maou.myapplicationtest.data.source.remote.response

import com.squareup.moshi.Json

data class AuthResponse(
    @field:Json(name = "token_type")
    val tokenType: String? = null,
    @field:Json(name = "expires_id")
    val expiresId: Long? = null,
    @field:Json(name = "access_token")
    val accessToken: String? = null,
    val error: String? = null,
    @field:Json(name = "error_description")
    val errorDescription: String? = null,
    val hint: String? = null,
    val message: String? = null
)
