package com.aaronnewton.makeitbakeitmvvm.presentation.cakes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aaronnewton.makeitbakeitmvvm.R

class CakesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cakes)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,
                CakesFragment.newInstance()
            )
            .commit()
    }
}
