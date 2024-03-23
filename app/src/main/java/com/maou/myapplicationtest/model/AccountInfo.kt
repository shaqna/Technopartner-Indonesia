package com.maou.myapplicationtest.model

data class AccountInfo(
    val greeting: String,
    val name: String,
    val saldo: Int,
    val point: Int,
    val qrcode: String,
    val banner: List<String>
)
