package com.aaronnewton.makeitbakeitmvvm.ui.cakes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aaronnewton.makeitbakeitmvvm.R
import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class CakesFragment : Fragment() {

    private val viewModel: CakesViewModel by viewModel()

    private val disposables = CompositeDisposable()

    companion object {
        fun newInstance() = CakesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cakes, container, false)

    override fun onStart() {
        super.onStart()
        viewModel.onStateChanged(::onStateChange).addTo(disposables)
        viewModel.fetchCakes()
    }

    override fun onStop() {
        disposables.clear()
        super.onStop()
    }

    private fun onStateChange(state: CakesState) = when (state) {
        is CakesState.Loading -> TODO() //show progress
        is CakesState.Successful -> displayCakes(state.cakes)
        is CakesState.Error -> showToast(state.error)
    }

    private fun displayCakes(cakes: List<Cake>) {
        Log.d("CakesFragment", "displayCakes: $cakes")
    }

    private fun showToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

}
