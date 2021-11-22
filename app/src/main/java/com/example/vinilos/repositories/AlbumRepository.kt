package com.example.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.models.Collector
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import org.json.JSONObject

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album>{


        var albums = getAlbumsCach(0)

        return if(albums.isNullOrEmpty()){


            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                albums = NetworkServiceAdapter.getInstance(application).getAlbums()
                addAlbumsCach(0, albums)
                albums
            }
        } else albums


        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums(0)
        if(potentialResp.isEmpty()){
            Log.d("Cache decision", "get from network")
            var comments = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums(0, comments)
            return comments
        }
        else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            return potentialResp
        }

        //return NetworkServiceAdapter.getInstance(application).getAlbums()

    }

    suspend fun getAlbumsCach(albumId:Int): List<Album>{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.ALBUMS_SPREFS)

        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Album>>(storedVal)
            }
        }
        return listOf<Album>()
    }

    suspend fun addAlbumsCach(albumId:Int, comments: List<Album>){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.ALBUMS_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }
    fun createAlbum(body: JSONObject, callback: (JSONObject)->Unit, onError: (VolleyError)->Unit) {
        NetworkServiceAdapter.getInstance(application).postCreateAlbum(body,{

            callback(it)
        },
            onError
        )
    }
}