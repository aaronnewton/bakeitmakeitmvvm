package com.aaronnewton.makeitbakeitmvvm.domain.repository

import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.Single

interface ApiRepository {

    fun getCakes(): Single<List<Cake>>

}

