package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentCollectorDetailBinding
import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.models.CollectorAlbums
import com.example.vinilos.models.CollectorDetail
import com.example.vinilos.ui.adapters.*
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.CollectorAlbumsViewModel
import com.example.vinilos.viewmodels.CollectorDetailViewModel
import com.squareup.picasso.Picasso


class CollectorDetailFragment : Fragment() {
    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var viewModel1: CollectorDetailViewModel
    private lateinit var viewModel2: CollectorAlbumsViewModel
    private var viewModelAdapterAlbum: CollectorAlbumsAdapter? = null
    private var viewModelAdapterPerformer: CollectorPerformersAdapter? = null
    private var viewModelAdapterComment: CollectorCommentsAdapter? = null



    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapterAlbum = CollectorAlbumsAdapter()
        viewModelAdapterPerformer = CollectorPerformersAdapter()
        viewModelAdapterComment = CollectorCommentsAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: CollectorDetailFragmentArgs by navArgs()
        view.findViewById<Button>(R.id.add_collector_album).setOnClickListener {
            val action = CollectorDetailFragmentDirections.actionCollectorDetailFragmentToCollectorAlbumFragment(args.collectorId)
          findNavController().navigate(action)
        }
        recyclerView1 = binding.collectorAlbums
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView1.adapter = viewModelAdapterAlbum

        recyclerView2 = binding.collectorPerfomers
        recyclerView2.layoutManager = LinearLayoutManager(context)
        recyclerView2.adapter = viewModelAdapterPerformer

        recyclerView3 = binding.collectorComments
        recyclerView3.layoutManager = LinearLayoutManager(context)
        recyclerView3.adapter = viewModelAdapterComment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Detalle Album"
        val args: CollectorDetailFragmentArgs by navArgs()

        viewModel1 = ViewModelProvider(this, CollectorDetailViewModel.Factory(activity.application, args.collectorId)).get(
            CollectorDetailViewModel::class.java)
        viewModel1.collectorDetail.observe(viewLifecycleOwner, Observer<CollectorDetail> {
            Log.d("Resp2", it.toString())
            it.apply {
                val view = binding.root
                view.findViewById<TextView>(R.id.collector_name).setText(this.name)
                view.findViewById<TextView>(R.id.collector_telephone).setText(this.telephone)
                view.findViewById<TextView>(R.id.collector_email).setText(this.email)

                viewModelAdapterPerformer!!.performers = this.favoritePerformers
                viewModelAdapterComment!!.comments = this.comments

            }
        })
        viewModel1.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })



        viewModel2 = ViewModelProvider(this, CollectorAlbumsViewModel.Factory(activity.application, args.collectorId)).get(
            CollectorAlbumsViewModel::class.java)
        viewModel2.albums.observe(viewLifecycleOwner, Observer<List<CollectorAlbums>> {
            it.apply {
                viewModelAdapterAlbum!!.albums = this
            }
        })
        viewModel2.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel1.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel1.onNetworkErrorShown()
        }
        if(!viewModel2.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel2.onNetworkErrorShown()
        }
    }
}