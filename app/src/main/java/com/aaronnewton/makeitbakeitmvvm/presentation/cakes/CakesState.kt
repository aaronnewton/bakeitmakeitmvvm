package com.aaronnewton.makeitbakeitmvvm.presentation.cakes

import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake

sealed class CakesState {

    object Loading : CakesState()

    data class Successful(val cakes: List<Cake>) : CakesState()

    data class Error(val error: String) : CakesState()

//    object Error : CakesState()

}