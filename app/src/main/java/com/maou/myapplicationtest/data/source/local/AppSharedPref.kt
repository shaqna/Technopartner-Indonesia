package com.maou.myapplicationtest.data.source.local

import android.content.SharedPreferences

open class AppSharedPref(private val prefs: SharedPreferences) {

    fun saveToken(token: String) {
        val bearerToken = "Bearer $token"
        prefs.edit().putString(ACCESS_TOKEN, bearerToken).apply()
    }

    fun fetchToken(): String? {
        return prefs.getString(ACCESS_TOKEN, null)
    }

    fun deleteToken() {
        prefs.edit().putString(ACCESS_TOKEN, null).apply()
    }

    companion object {
        const val APP_SHARED_PREFS = "APP_SHARED_PREFS"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }
}