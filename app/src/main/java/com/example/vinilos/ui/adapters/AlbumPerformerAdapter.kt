package com.example.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumPerformerBinding
import com.example.vinilos.databinding.AlbumTrackBinding
import com.example.vinilos.models.Performer
import com.example.vinilos.models.Track
import com.squareup.picasso.Picasso

class AlbumPerformerAdapter: RecyclerView.Adapter<AlbumPerformerAdapter.AlbumPerformerViewHolder>() {

    var performers :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPerformerViewHolder {
        val withDataBinding: AlbumPerformerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumPerformerViewHolder.LAYOUT,
            parent,
            false)
        return AlbumPerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumPerformerViewHolder, position: Int) {
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


    class AlbumPerformerViewHolder(val viewDataBinding: AlbumPerformerBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_performer
        }
    }
}