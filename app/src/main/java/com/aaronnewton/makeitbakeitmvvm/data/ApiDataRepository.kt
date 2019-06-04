package com.aaronnewton.makeitbakeitmvvm.data

import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import com.aaronnewton.makeitbakeitmvvm.domain.repository.ApiRepository
import io.reactivex.Single

class ApiDataRepository constructor(
    private val api: Api
) : ApiRepository {

    override fun getCakes(): Single<List<Cake>> = api.getCakes()
        .map { ResponseMapper.transform(it) }
}