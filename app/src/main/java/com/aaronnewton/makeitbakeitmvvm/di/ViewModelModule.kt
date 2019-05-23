package com.aaronnewton.makeitbakeitmvvm.di

import com.aaronnewton.makeitbakeitmvvm.ui.cakes.CakesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { CakesViewModel(get()) }
}