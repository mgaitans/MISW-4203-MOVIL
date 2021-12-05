package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentMusicianListBinding
import com.example.vinilos.ui.adapters.BandsAdapter
import com.example.vinilos.ui.adapters.MusiciansAdapter
import com.example.vinilos.viewmodels.BandViewModel
import com.example.vinilos.viewmodels.MusicianViewModel


class MusicianListFragment : Fragment() {

    private var _binding: FragmentMusicianListBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MusicianViewModel
    private var viewModelAdapter: MusiciansAdapter? = null

    private lateinit var recyclerView2: RecyclerView
    private lateinit var viewModel2: BandViewModel
    private var viewModelAdapter2: BandsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        _binding = FragmentMusicianListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = MusiciansAdapter()
        viewModelAdapter2 = BandsAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.fragmentsMisi
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        recyclerView2 = binding.fragmentsBand
        recyclerView2.layoutManager = LinearLayoutManager(context)
        recyclerView2.adapter = viewModelAdapter2

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_musicians)
        viewModel = ViewModelProvider(this, MusicianViewModel.Factory(activity.application)).get(MusicianViewModel::class.java)

        viewModel.musicians.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter!!.musicians = this
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })



        viewModel2 = ViewModelProvider(this, BandViewModel.Factory(activity.application)).get(BandViewModel::class.java)

        viewModel2.musicians.observe(viewLifecycleOwner, {
            it.apply {
                viewModelAdapter2!!.musicians = this
                Log.d("act", this.toString())
            }
        })
        viewModel2.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
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

        if(!viewModel2.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel2.onNetworkErrorShown()
        }
    }

}