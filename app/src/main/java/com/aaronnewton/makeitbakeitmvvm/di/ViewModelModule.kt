package com.aaronnewton.makeitbakeitmvvm.di

import com.aaronnewton.makeitbakeitmvvm.ui.CakeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CakeViewModel(get()) }
}