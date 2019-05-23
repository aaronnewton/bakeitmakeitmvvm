package com.aaronnewton.makeitbakeitmvvm.data

import retrofit2.Response

object ResponseMapper {

    fun <T> transform(response: Response<T>): T {
        if (response.isSuccessful)
            return response.body() ?: error("response is null")
        else
            throw BackendException(response.code())
    }

    fun transformEmpty(response: Response<Unit>) {
        if (response.isSuccessful)
            return
        else
            throw BackendException(response.code())
    }
}