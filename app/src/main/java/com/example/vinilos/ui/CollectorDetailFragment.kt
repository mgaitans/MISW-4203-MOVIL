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
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.ui.adapters.AlbumCommentAdapter
import com.example.vinilos.ui.adapters.AlbumDetailAdapter
import com.example.vinilos.ui.adapters.AlbumPerformerAdapter
import com.example.vinilos.viewmodels.AlbumDetailViewModel
import com.squareup.picasso.Picasso


class CollectorDetailFragment : Fragment() {
    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args: CollectorDetailFragmentArgs by navArgs()
        view.findViewById<Button>(R.id.add_collector_album).setOnClickListener {
            val action = CollectorDetailFragmentDirections.actionCollectorDetailFragmentToCollectorAlbumFragment(args.collectorId)
          findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = "Detalle Album"
        val args: AlbumDetailFragmentArgs by navArgs()



    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {

    }
}