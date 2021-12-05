package com.example.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.vinilos.models.Musician
import com.example.vinilos.models.Performer
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MusicianRepository (val application: Application){
    suspend fun refreshData(): List<Performer>{


        var musicians = getMusiciansCach(0)

        return if(musicians.isNullOrEmpty()){


            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                musicians = NetworkServiceAdapter.getInstance(application).getMusicians()
                musicians
            }
        } else musicians

        //return NetworkServiceAdapter.getInstance(application).getMusicians()

    }

    suspend fun getMusiciansCach(musicianId:Int): List<Performer>{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.MUSICIANS_SPREFS)

        if(prefs.contains(musicianId.toString())){
            val storedVal = prefs.getString(musicianId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Performer>>(storedVal)
            }
        }
        return listOf<Performer>()
    }

    suspend fun addMusiciansCach(musicianId:Int, comments: List<Performer>){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.MUSICIANS_SPREFS)
        if(!prefs.contains(musicianId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(musicianId.toString(), store)
                apply()
            })
        }
    }

}