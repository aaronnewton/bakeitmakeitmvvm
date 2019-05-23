package com.aaronnewton.makeitbakeitmvvm.data

import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.Single

interface ApiRepository {
    fun getCakes(): Single<List<Cake>>
}

class ApiDataRepository constructor(
    private val api: Api
) : ApiRepository {

    override fun getCakes(): Single<List<Cake>> = api.getCakes()
        .map { ResponseMapper.transform(it) }
}