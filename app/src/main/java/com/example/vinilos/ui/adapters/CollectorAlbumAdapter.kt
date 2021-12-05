package com.example.vinilos.ui.adapters


import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.CheckBox
import androidx.annotation.LayoutRes
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilos.R

import com.example.vinilos.databinding.CollectorAlbumItemBinding
import com.example.vinilos.models.Album


class CollectorAlbumAdapter  : RecyclerView.Adapter<CollectorAlbumAdapter.CollectorAlbumViewHolder>(){
    val checkedAlbums = ArrayList<Album>()
    var albums :List<Album> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumViewHolder {
        val withDataBinding: CollectorAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorAlbumViewHolder.LAYOUT,
            parent,
            false)
        return CollectorAlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorAlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]

            //Picasso.get()
                //.load(it.album?.cover)
                //.into(it.diceImage)
        }
        holder.bind(albums[position])
        holder.viewDataBinding.radiobutton.setOnClickListener {
            val item = it.findViewById<CheckBox>(R.id.radiobutton)
            if(item.isChecked){
                checkedAlbums.add(albums[position])
            }else if(!item.isChecked){
                checkedAlbums.remove(albums[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class CollectorAlbumViewHolder(val viewDataBinding: CollectorAlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_album_item
        }
        fun bind(album: Album) {
            Glide.with(itemView)
                .load(album.cover.toUri().buildUpon().scheme("https").build())
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.load_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.broken_image))
                .into(viewDataBinding.diceImage)
        }
    }
}