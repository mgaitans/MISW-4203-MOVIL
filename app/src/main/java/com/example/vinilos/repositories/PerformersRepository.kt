package com.example.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

import com.example.vinilos.models.*
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PerformersRepository (val application: Application) {

    suspend fun getDetailBand(bandId: Int): Band {
        var band = getBandCach(bandId)
        return if(band.id === 0){

            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Band(0,"","","","",
                    emptyList<Album>())
            } else {
                band = NetworkServiceAdapter.getInstance(application).getBand(bandId)
                addBandCach(bandId, band)
                band
            }
        } else band
    }

    suspend fun getBandCach(albumId:Int): Band {
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.BAND_DETAIL_SPREFS)

        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<Band>(storedVal)
            }
        }
        return Band(0,"","","","",
            emptyList<Album>())
    }

    suspend fun addBandCach(bandId:Int, comments: Band){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.BAND_DETAIL_SPREFS)
        if(!prefs.contains(bandId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(bandId.toString(), store)
                apply()
            })
        }
    }




    suspend fun getDetailMusician(musicianId: Int): Musician {
        var musician = getMusicianCach(musicianId)
        return if(musician.id === 0){

            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                Musician(0,"","","","",
                    emptyList<Album>())
            } else {
                musician = NetworkServiceAdapter.getInstance(application).getMusician(musicianId)
                addMusicianCach(musicianId, musician)
                musician
            }
        } else musician



    }

    suspend fun getMusicianCach(albumId:Int): Musician {
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.MUSICIAN_DETAIL_SPREFS)

        if(prefs.contains(albumId.toString())){
            val storedVal = prefs.getString(albumId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<Musician>(storedVal)
            }
        }
        return Musician(0,"","","","",
            emptyList<Album>())
    }

    suspend fun addMusicianCach(bandId:Int, comments: Musician){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.MUSICIAN_DETAIL_SPREFS)
        if(!prefs.contains(bandId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(bandId.toString(), store)
                apply()
            })
        }
    }


}