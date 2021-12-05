package com.example.vinilos.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilos.R

import com.example.vinilos.databinding.MusicianItemBinding

import com.example.vinilos.models.Performer

import com.example.vinilos.ui.MusicianListFragmentDirections

class MusiciansAdapter : RecyclerView.Adapter<MusiciansAdapter.MusicianViewHolder>(){
    var musicians :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianViewHolder.LAYOUT,
            parent,
            false)
        return MusicianViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusiciansAdapter.MusicianViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = musicians[position]
            //Picasso.get()
            //.load(it.album?.cover)
            //.into(it.diceImage)
        }
        holder.bind(musicians[position])
        holder.viewDataBinding.root.setOnClickListener {
            val action = MusicianListFragmentDirections.actionMusicianListFragmentToBandDetailFragment(musicians[position].performerId, false)
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }

    class MusicianViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }

        fun bind(musician: Performer) {
            Glide.with(itemView)
                .load(musician.image.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.load_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.broken_image))
                .into(viewDataBinding.musicianImage)
        }
    }
}