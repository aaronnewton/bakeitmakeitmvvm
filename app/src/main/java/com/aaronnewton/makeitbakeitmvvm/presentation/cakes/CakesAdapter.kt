package com.aaronnewton.makeitbakeitmvvm.presentation.cakes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aaronnewton.makeitbakeitmvvm.R
import com.aaronnewton.makeitbakeitmvvm.data.entities.Cake
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cake_item.view.*

class CakesAdapter(
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<CakesAdapter.CakeViewHolder>() {

    var cakes: MutableList<Cake> = mutableListOf()

    fun addCakes(items: List<Cake>) {
        cakes.clear()
        cakes.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cakes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        return CakeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cake_item, parent, false), callback
        )
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        holder.bind(cakes[position], position)
    }

    class CakeViewHolder(
        view: View,
        private val callback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private var kegIndex: Int = 0

        fun bind(item: Cake, position: Int) = with(itemView) {
            kegIndex = position
            cake_text.text = item.title

            Picasso.get()
                .load(item.image)
                .error(R.mipmap.ic_launcher)
                .into(cake_image)

            setOnClickListener { callback(kegIndex) }
        }
    }

}