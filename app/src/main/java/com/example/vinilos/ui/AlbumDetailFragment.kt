package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentAlbumDetailBinding
import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.ui.adapters.AlbumCommentAdapter
import com.example.vinilos.ui.adapters.AlbumDetailAdapter
import com.example.vinilos.ui.adapters.AlbumPerformerAdapter
//import com.example.vinilos.ui.adapters.AlbumDetailAdapter
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.example.vinilos.viewmodels.AlbumViewModel
import com.squareup.picasso.Picasso


class AlbumDetailFragment : Fragment() {
    private var _binding: FragmentAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapterTrack: AlbumDetailAdapter? = null
    private var viewModelAdapterPerformer: AlbumPerformerAdapter? = null
    private var viewModelAdapterComment: AlbumCommentAdapter? = null


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapterTrack = AlbumDetailAdapter()
        viewModelAdapterPerformer = AlbumPerformerAdapter()
        viewModelAdapterComment = AlbumCommentAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView1 = binding.albumTracks
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView1.adapter = viewModelAdapterTrack

        recyclerView2 = binding.albumPerformers
        recyclerView2.layoutManager = LinearLayoutManager(context)
        recyclerView2.adapter = viewModelAdapterPerformer

        recyclerView3 = binding.albumComments
        recyclerView3.layoutManager = LinearLayoutManager(context)
        recyclerView3.adapter = viewModelAdapterComment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Detalle Album"
        val args: AlbumDetailFragmentArgs by navArgs()
        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(activity.application, args.albumId)).get(
            AlbumDetailViewModel::class.java)
        viewModel.albumDetail.observe(viewLifecycleOwner, Observer<AlbumDetail> {
            Log.d("Resp2", it.toString())
            it.apply {
                val view = binding.root
                view.findViewById<TextView>(R.id.album_name).setText(this.name)
                view.findViewById<TextView>(R.id.album_release).setText(this.releaseDate)
                view.findViewById<TextView>(R.id.album_description).setText(this.description)
                view.findViewById<TextView>(R.id.album_genre).setText(this.genre)
                view.findViewById<TextView>(R.id.album_record).setText(this.recordLabel)
                Picasso.get()
                    .load(this.cover)
                    .into(view.findViewById<ImageView>(R.id.album_image))
                viewModelAdapterTrack!!.tracks = this.tracks
                viewModelAdapterPerformer!!.performers = this.performers
                viewModelAdapterComment!!.comments = this.comments

            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}