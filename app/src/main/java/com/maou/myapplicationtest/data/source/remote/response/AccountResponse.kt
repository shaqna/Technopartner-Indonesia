package com.maou.myapplicationtest.data.source.remote.response

import com.maou.myapplicationtest.model.AccountInfo

data class AccountResponse(
    val status: String,
    val result: AccountInfo
)
