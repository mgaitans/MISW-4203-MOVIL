package com.example.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.android.volley.VolleyError
import com.example.vinilos.models.*
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AlbumDetailRepository (val application: Application){

    suspend fun refreshData(albumId: Int): AlbumDetail{


        var album = getAlbumCach(albumId)
        Log.d("Cache decision", albumId.toString())
        return if(album.albumId === 0){

            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                AlbumDetail(0,"","","","","","",
                    emptyList<Track>(),emptyList<Performer>(),emptyList<Comment>())
            } else {
                album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
                addAlbumCach(albumId, album)
                album
            }
        } else album




        var potentialResp = CacheManager.getInstance(application.applicationContext).getAlbumDetail(albumId)
        if(potentialResp.albumId == 0){
            Log.d("Cache decision", "get from network")
            var comments = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbumDetail(albumId, comments)
            return comments
        }
        else{
            return potentialResp
        }

    }


    suspend fun getAlbumCach(albumId:Int): AlbumDetail{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.ALBUM_DETAIL_SPREFS)

        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<AlbumDetail>(storedVal)
            }
        }
        return AlbumDetail(0,"","","","","","",
            emptyList<Track>(),emptyList<Performer>(),emptyList<Comment>())
    }

    suspend fun addAlbumCach(albumId:Int, comments: AlbumDetail){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.ALBUM_DETAIL_SPREFS)
        if(!prefs.contains(albumId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(albumId.toString(), store)
                apply()
            })
        }
    }


}