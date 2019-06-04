package com.aaronnewton.makeitbakeitmvvm.di

import com.aaronnewton.makeitbakeitmvvm.AppConfig
import com.aaronnewton.makeitbakeitmvvm.data.ApiDataRepository
import com.aaronnewton.makeitbakeitmvvm.domain.repository.ApiRepository
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val CONNECT_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 15L
private const val READ_TIMEOUT = 15L
private const val MAX_CACHE_SIZE = 10 * 1024 * 1024L

val networkModule = module {
    single { Cache(androidApplication().cacheDir, MAX_CACHE_SIZE) }

    single { GsonBuilder().create() }

    single {
        OkHttpClient.Builder().apply {
            cache(get())
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(AppConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(get())
            .build()
    }

    single<ApiRepository> { ApiDataRepository(get()) }

}