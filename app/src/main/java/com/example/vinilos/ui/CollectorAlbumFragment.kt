package com.example.vinilos.ui

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentCollectorAlbumBinding
import com.example.vinilos.models.Album

import com.example.vinilos.ui.adapters.CollectorAlbumAdapter

import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.CollectorAlbumViewModel

import com.google.android.material.textfield.TextInputEditText


class CollectorAlbumFragment : Fragment() {
    private var _binding: FragmentCollectorAlbumBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModelList: AlbumViewModel
    private lateinit var viewModelPost: CollectorAlbumViewModel
    private var viewModelAdapter: CollectorAlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorAlbumAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.collectorAlbums
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)

            view.findViewById<Button>(R.id.add_collector_album_btn).setOnClickListener {
                val albums = viewModelAdapter!!.checkedAlbums
                val args: CollectorAlbumFragmentArgs by navArgs()
                val price = view.findViewById<TextInputEditText>(R.id.txt_price).text.toString()
                val status = view.findViewById<TextInputEditText>(R.id.txt_status).text.toString()


                    viewModelPost = ViewModelProvider(this, CollectorAlbumViewModel.Factory(activity.application, args.collectorId, albums,price,status)).get(
                        CollectorAlbumViewModel::class.java)
                Toast.makeText(activity, "Agregado Exitosamente!", Toast.LENGTH_LONG).show()


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)
        viewModelList = ViewModelProvider(this, AlbumViewModel.Factory(activity.application)).get(
            AlbumViewModel::class.java)
        viewModelList.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
            it.apply {
                viewModelAdapter!!.albums = this
            }
        })
        viewModelList.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModelList.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModelList.onNetworkErrorShown()
        }
    }
}