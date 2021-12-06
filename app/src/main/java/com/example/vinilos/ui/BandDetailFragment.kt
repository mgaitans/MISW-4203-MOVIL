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
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.vinilos.R

import com.example.vinilos.databinding.FragmentBandDetailBinding

import com.example.vinilos.models.Band
import com.example.vinilos.models.Musician

import com.example.vinilos.ui.adapters.AlbumsAdapter

import com.example.vinilos.viewmodels.BandDetailViewModel
import com.example.vinilos.viewmodels.MusicianDetailViewModel


class BandDetailFragment : Fragment() {
    private var _binding: FragmentBandDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView1: RecyclerView
    private lateinit var viewModelBand: BandDetailViewModel
    private lateinit var viewModelMusician: MusicianDetailViewModel
    private var viewModelAdapterTrack: AlbumsAdapter? = null


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBandDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapterTrack = AlbumsAdapter()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView1 = binding.bandAlbums
        recyclerView1.layoutManager = LinearLayoutManager(context)
        recyclerView1.adapter = viewModelAdapterTrack


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Detalle Album"
        val args: BandDetailFragmentArgs by navArgs()
        val type = args.type

        if(type){
            viewModelBand = ViewModelProvider(this, BandDetailViewModel.Factory(activity.application, args.bandId)).get(
                BandDetailViewModel::class.java)
            viewModelBand.albumDetail.observe(viewLifecycleOwner, Observer<Band> {
                Log.d("Resp2", it.toString())
                it.apply {
                    val view = binding.root
                    view.findViewById<TextView>(R.id.band_name).setText(this.name)
                    view.findViewById<TextView>(R.id.band_date).setText(this.creationDate)
                    view.findViewById<TextView>(R.id.band_description).setText(this.description)

                    Glide.with(view)
                        .load(this.image.toUri().buildUpon().scheme("https").build())
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.load_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.broken_image))
                        .into(view.findViewById<ImageView>(R.id.band_image))
                    viewModelAdapterTrack!!.albums = this.albums


                }
            })
            viewModelBand.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        }

        else{
            viewModelMusician = ViewModelProvider(this, MusicianDetailViewModel.Factory(activity.application, args.bandId)).get(
                MusicianDetailViewModel::class.java)
            viewModelMusician.albumDetail.observe(viewLifecycleOwner, Observer<Musician> {
                Log.d("Resp2", it.toString())
                it.apply {
                    val view = binding.root
                    view.findViewById<TextView>(R.id.band_name).setText(this.name)
                    view.findViewById<TextView>(R.id.band_date).setText(this.birthDate)
                    view.findViewById<TextView>(R.id.band_description).setText(this.description)

                    Glide.with(view)
                        .load(this.image.toUri().buildUpon().scheme("https").build())
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.load_image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.drawable.broken_image))
                        .into(view.findViewById<ImageView>(R.id.band_image))
                    viewModelAdapterTrack!!.albums = this.albums


                }
            })
            viewModelMusician.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModelBand.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModelBand.onNetworkErrorShown()
        }
    }
}