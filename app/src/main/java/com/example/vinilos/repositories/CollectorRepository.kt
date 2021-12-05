package com.example.vinilos.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log

import com.example.vinilos.models.*
import com.example.vinilos.network.CacheManager

import com.example.vinilos.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject


class CollectorRepository (val application: Application){
    suspend fun refreshData(): List<Collector> {


        var collectors = getCollectorsCach(0)

        return if(collectors.isNullOrEmpty()){


            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                collectors = NetworkServiceAdapter.getInstance(application).getCollectors()
                addCollectorsCach(0, collectors)
                collectors
            }
        } else collectors





        //return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun addAlbum(collectorId: Int,albumId: Int,body: JSONObject) : JSONObject {
        val resp = NetworkServiceAdapter.getInstance(application).postCollectorAlbum(body,collectorId,albumId)
        val newAlbms = NetworkServiceAdapter.getInstance(application).getCollectorAlbums(collectorId)

        val format = Json {  }
        var store = format.encodeToString(newAlbms)
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTOR_ALBUMS_SPREFS)
        with(prefs.edit(),{
            putString(collectorId.toString(), store)
            apply()
        })
        return resp
    }

    suspend fun getAlbums(collectorId: Int) : List<CollectorAlbums> {

        var albums = getAlbumsCach(collectorId)

        return if(albums.isNullOrEmpty()){


            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                emptyList()
            } else {
                albums = NetworkServiceAdapter.getInstance(application).getCollectorAlbums(collectorId)
                addAlbumsCach(collectorId, albums)
                albums
            }
        } else albums
    }






    suspend fun getCollector(collectorId: Int) : CollectorDetail {


        var collector = getCollectorCach(collectorId)
        Log.d("Cache decision", collector.toString())
        return if(collector.collectorId === 0){

            val cm = application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if( cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_WIFI && cm.activeNetworkInfo?.type != ConnectivityManager.TYPE_MOBILE){
                CollectorDetail(0,"","","",
                    emptyList<Comment>(),emptyList<Performer>())
            } else {
                collector = NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
                addCollectorCach(collectorId, collector)
                collector
            }
        } else collector


    }


    //Cache collector albums
    suspend fun getAlbumsCach(collectorId:Int): List<CollectorAlbums>{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTOR_ALBUMS_SPREFS)

        if(prefs.contains(collectorId.toString())){
            val storedVal = prefs.getString(collectorId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<CollectorAlbums>>(storedVal)
            }
        }
        return listOf<CollectorAlbums>()
    }

    suspend fun addAlbumsCach(collectorId:Int, comments: List<CollectorAlbums>){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTOR_ALBUMS_SPREFS)
        if(!prefs.contains(collectorId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(collectorId.toString(), store)
                apply()
            })
        }
    }

    //Cache Collector Detail
    suspend fun getCollectorCach(collectorId:Int): CollectorDetail{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTOR_DETAIL_SPREFS)

        if(prefs.contains(collectorId.toString())){
            val storedVal = prefs.getString(collectorId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<CollectorDetail>(storedVal)
            }
        }
        return CollectorDetail(0,"","","",
            emptyList<Comment>(),emptyList<Performer>())
    }

    suspend fun addCollectorCach(collectorId:Int, comments: CollectorDetail){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTOR_DETAIL_SPREFS)
        if(!prefs.contains(collectorId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(collectorId.toString(), store)
                apply()
            })
        }
    }

    //Cache collectors
    suspend fun getCollectorsCach(collectorId:Int): List<Collector>{
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTORS_SPREFS)

        if(prefs.contains(collectorId.toString())){
            val storedVal = prefs.getString(collectorId.toString(), "")

            if(!storedVal.isNullOrBlank()){
                return format.decodeFromString<List<Collector>>(storedVal)
            }
        }
        return listOf<Collector>()
    }

    suspend fun addCollectorsCach(collectorId:Int, comments: List<Collector>){
        val format = Json {  }
        val prefs = CacheManager.SPrefsCache.getPrefs(application.baseContext, CacheManager.SPrefsCache.COLLECTORS_SPREFS)
        if(!prefs.contains(collectorId.toString())){
            var store = format.encodeToString(comments)
            with(prefs.edit(),{
                putString(collectorId.toString(), store)
                apply()
            })
        }
    }
}