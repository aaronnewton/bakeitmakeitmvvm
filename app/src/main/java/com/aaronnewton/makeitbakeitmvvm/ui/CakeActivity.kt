package com.aaronnewton.makeitbakeitmvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.aaronnewton.makeitbakeitmvvm.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class CakeActivity : AppCompatActivity() {

    private val cakeViewModel: CakeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cakeViewModel.fetchCakes()
    }
}
