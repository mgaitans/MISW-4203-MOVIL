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
import com.example.vinilos.databinding.AlbumPerformerBinding
import com.example.vinilos.databinding.CollectorAlbumBinding
import com.example.vinilos.databinding.CollectorAlbumItemBinding
import com.example.vinilos.models.Album
import com.example.vinilos.models.CollectorAlbums
import com.example.vinilos.models.Performer
import com.example.vinilos.ui.AlbumListFragmentDirections
import com.squareup.picasso.Picasso

class CollectorAlbumsAdapter : RecyclerView.Adapter<CollectorAlbumsAdapter.CollectorAlbumViewHolder>(){

    var albums :List<CollectorAlbums> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumViewHolder {
        val withDataBinding: CollectorAlbumBinding = DataBindingUtil.inflate(
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
                //.load(it.album?.album?.cover)
                //.into(it.diceImage)
        }
        holder.bind(albums[position].album)
    }

    override fun getItemCount(): Int {
        return albums.size
    }


    class CollectorAlbumViewHolder(val viewDataBinding: CollectorAlbumBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_album
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