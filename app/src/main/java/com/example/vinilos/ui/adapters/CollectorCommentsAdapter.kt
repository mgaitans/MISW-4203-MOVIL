package com.example.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumCommentBinding
import com.example.vinilos.databinding.CollectorCommentBinding
import com.example.vinilos.models.Comment

class CollectorCommentsAdapter : RecyclerView.Adapter<CollectorCommentsAdapter.CollectorCommentViewHolder>(){

    var comments :List<Comment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorCommentViewHolder {
        val withDataBinding: CollectorCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorCommentViewHolder.LAYOUT,
            parent,
            false)
        return CollectorCommentViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorCommentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = comments[position]
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }


    class CollectorCommentViewHolder(val viewDataBinding: CollectorCommentBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_comment
        }
    }
}