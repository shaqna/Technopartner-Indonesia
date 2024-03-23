package com.maou.myapplicationtest.data.di

import android.content.Context
import com.maou.myapplicationtest.BuildConfig
import com.maou.myapplicationtest.data.repository.auth.AuthRepository
import com.maou.myapplicationtest.data.repository.home.HomeRepository
import com.maou.myapplicationtest.data.repository.menu.MenuRepository
import com.maou.myapplicationtest.data.source.local.AppSharedPref
import com.maou.myapplicationtest.data.source.remote.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val retrofit = module {
    single {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            ).build()

        retrofit.create(ApiService::class.java)
    }
}

private val sharedPrefsModule = module {
    single {
        androidApplication().getSharedPreferences(AppSharedPref.APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}

private val appPrefsModule = module {
    singleOf(::AppSharedPref)
}
private val repositoryModule = module {
    singleOf(::AuthRepository)
    singleOf(::HomeRepository)
    singleOf(::MenuRepository)
}

val dataModule = module {
    includes(retrofit, sharedPrefsModule, appPrefsModule, repositoryModule)
}