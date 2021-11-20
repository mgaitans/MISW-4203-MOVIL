package com.example.vinilos.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.databinding.FragmentAlbumCreateBinding
import com.example.vinilos.viewmodels.AlbumCreateViewModel
import com.google.android.material.textfield.TextInputEditText

class AlbumCreateFragment : Fragment() {

    private var _binding: FragmentAlbumCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelCreate: AlbumCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumCreateBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)

        view.findViewById<Button>(R.id.create_album).setOnClickListener {
            val name = view.findViewById<TextInputEditText>(R.id.album_name).text.toString()
            val cover = view.findViewById<TextInputEditText>(R.id.album_cover).text.toString()
            val description = view.findViewById<TextInputEditText>(R.id.album_description).text.toString()

            val releaseYear = view.findViewById<DatePicker>(R.id.album_release).year.toString()
            val releaseMonth = view.findViewById<DatePicker>(R.id.album_release).month.toString()
            val releaseDay = view.findViewById<DatePicker>(R.id.album_release).dayOfMonth.toString()
            val releaseDate = releaseYear+"-"+releaseMonth+"-"+releaseDay
            val genre = view.findViewById<TextInputEditText>(R.id.album_genre).text.toString()
            val recordLabel = view.findViewById<TextInputEditText>(R.id.album_record).text.toString()

            viewModelCreate = ViewModelProvider(this, AlbumCreateViewModel.Factory(activity.application, name, cover, releaseDate, description, genre,recordLabel)).get(
                AlbumCreateViewModel::class.java)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_albums)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModelCreate.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModelCreate.onNetworkErrorShown()
        }
    }
}