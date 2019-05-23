package com.aaronnewton.makeitbakeitmvvm.data

import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET


interface Api {

    @GET("waracle_cake-android-client")
    fun getCakes(): Single<Response<List<Cake>>>

}