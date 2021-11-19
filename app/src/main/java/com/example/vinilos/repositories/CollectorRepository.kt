package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.Collector
import com.example.vinilos.network.NetworkServiceAdapter
import org.json.JSONObject

class CollectorRepository (val application: Application){
    fun refreshData(callback: (List<Collector>)->Unit, onError: (VolleyError)->Unit) {
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        NetworkServiceAdapter.getInstance(application).getCollectors({
            callback(it)
        },
            onError
        )
    }

    fun addAlbum(collectorId: Int,albumId: Int,body: JSONObject,callback: (JSONObject)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).postCollectorAlbum(body,collectorId,albumId,{

            callback(it)
        },
            onError
        )
    }
}