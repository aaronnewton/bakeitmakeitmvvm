package com.aaronnewton.makeitbakeitmvvm.di

import com.aaronnewton.makeitbakeitmvvm.data.Api
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(Api::class.java) }
}