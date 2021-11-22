package com.example.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumPerformerBinding
import com.example.vinilos.databinding.CollectorPerformerBinding
import com.example.vinilos.models.Performer
import com.squareup.picasso.Picasso

class CollectorPerformersAdapter : RecyclerView.Adapter<CollectorPerformersAdapter.CollectorPerformerViewHolder>() {


    var performers :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorPerformerViewHolder {
        val withDataBinding: CollectorPerformerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorPerformerViewHolder.LAYOUT,
            parent,
            false)
        return CollectorPerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorPerformerViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = performers[position]

            Picasso.get()
                .load(it.performer?.image)
                .into(it.performerImage)
        }
    }

    override fun getItemCount(): Int {
        return performers.size
    }


    class CollectorPerformerViewHolder(val viewDataBinding: CollectorPerformerBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_performer
        }
    }
}