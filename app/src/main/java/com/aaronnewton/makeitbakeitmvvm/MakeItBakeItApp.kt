package com.aaronnewton.makeitbakeitmvvm

import android.app.Application
import com.aaronnewton.makeitbakeitmvvm.di.apiModule
import com.aaronnewton.makeitbakeitmvvm.di.networkModule
import com.aaronnewton.makeitbakeitmvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MakeItBakeItApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MakeItBakeItApp)
            androidLogger()
            modules(networkModule, apiModule, viewModelModule)
        }

    }
}