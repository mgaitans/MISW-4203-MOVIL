package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumDetailRepository (val application: Application){

    fun refreshData(albumId: Int, callback: (AlbumDetail)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId,{
            Log.d("respuesta", it.toString())
            callback(it)
        },
            onError
        )
    }
}