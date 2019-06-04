package com.aaronnewton.makeitbakeitmvvm.ui.cakes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaronnewton.makeitbakeitmvvm.R
import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_cakes.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.DividerItemDecoration

class CakesFragment : Fragment() {

    private val viewModel: CakesViewModel by viewModel()

    private val disposables = CompositeDisposable()

    private val adapter: CakesAdapter = CakesAdapter(::onCakeClicked)

    companion object {
        fun newInstance() = CakesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cakes_recycle_view.layoutManager = LinearLayoutManager(context)
        cakes_recycle_view.adapter = adapter
        cakes_recycle_view.addItemDecoration(
            DividerItemDecoration(
                cakes_recycle_view.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

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
        adapter.addCakes(cakes)
    }

    private fun showToast(message: String) = Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

    private fun onCakeClicked(index: Int) {
        Log.d("CakesFragment", "onCakeClicked: $index")

        AlertDialog.Builder(requireContext())
            .setTitle(adapter.cakes[index].title)
            .setMessage(adapter.cakes[index].desc)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes) { dialog, _ -> dialog.dismiss() }
            .show()
    }

}
