package com.example.vinilos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentCollectorAlbumBinding
import com.example.vinilos.models.Album
import com.example.vinilos.models.Collector
import com.example.vinilos.ui.adapters.CollectorAlbumAdapter
import com.example.vinilos.ui.adapters.CollectorsAdapter
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.CollectorAlbumViewModel



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