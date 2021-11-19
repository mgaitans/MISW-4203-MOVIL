package com.example.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumItemBinding
import com.example.vinilos.databinding.CollectorAlbumItemBinding
import com.example.vinilos.models.Album
import com.example.vinilos.ui.AlbumListFragmentDirections
import com.squareup.picasso.Picasso

class CollectorAlbumAdapter : RecyclerView.Adapter<CollectorAlbumAdapter.CollectorAlbumViewHolder>(){
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
            Picasso.get()
                .load(it.album?.cover)
                .into(it.diceImage)
        }
        holder.viewDataBinding.root.setOnClickListener {
            //val action = AlbumListFragmentDirections.actionAlbumListFragmentToAlbumDetailFragment(albums[position].albumId)
            // Navigate using that action
            //holder.viewDataBinding.root.findNavController().navigate(action)
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
    }
}