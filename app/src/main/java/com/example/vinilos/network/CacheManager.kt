package com.example.vinilos.network

import android.content.Context
import android.content.SharedPreferences
import android.util.SparseArray
import com.example.vinilos.models.*
import java.util.Collections.list

class CacheManager (context: Context){
    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }

    }

    object SPrefsCache {
        const val APP_SPREFS = "com.example.vinilos.app"
        const val COLLECTOR_ALBUMS_SPREFS = "com.example.vinilos.collectoralbums"
        const val COLLECTOR_DETAIL_SPREFS = "com.example.vinilos.collectordetail"
        const val COLLECTORS_SPREFS = "com.example.vinilos.collectors"
        const val ALBUM_DETAIL_SPREFS = "com.example.vinilos.albumdetail"
        const val ALBUMS_SPREFS = "com.example.vinilos.albums"
        const val MUSICIANS_SPREFS = "com.example.vinilos.musicians"
        const val BANDS_SPREFS = "com.example.vinilos.bands"
        const val BAND_DETAIL_SPREFS = "com.example.vinilos.banddetail"
        const val MUSICIAN_DETAIL_SPREFS = "com.example.vinilos.musiciandetail"

        fun getPrefs(context: Context, name:String): SharedPreferences{
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }

    //lista de collectors albums
    private var collectorAlbums: HashMap<Int, List<CollectorAlbums>> = hashMapOf()
    fun addComments(collectorId: Int, collectorAlbum: List<CollectorAlbums>){
        if (!collectorAlbums.containsKey(collectorId)){
            collectorAlbums[collectorId] = collectorAlbum
        }
    }
    fun getComments(collectorId: Int) : List<CollectorAlbums>{
        return if (collectorAlbums.containsKey(collectorId)) collectorAlbums[collectorId]!! else listOf<CollectorAlbums>()
    }

    //Elemento de AlbumDetail
    private var albumDetail: HashMap<Int, AlbumDetail> = hashMapOf()
    fun addAlbumDetail(albumId: Int, albumData: AlbumDetail){
        if (!albumDetail.containsKey(albumId)){
            albumDetail[albumId] = albumData
        }
    }
    fun getAlbumDetail(albumId: Int) : AlbumDetail{
        return if (albumDetail.containsKey(albumId)) albumDetail[albumId]!! else AlbumDetail(0,"","","","","","",
            emptyList<Track>(),emptyList<Performer>(),emptyList<Comment>())
    }

    //Elemento de CollectorDetail
    private var collectorDetail: HashMap<Int, CollectorDetail> = hashMapOf()
    fun addCollectorDetail(colelctorId: Int, albumDeta: CollectorDetail){
        if (!collectorDetail.containsKey(colelctorId)){
            collectorDetail[colelctorId] = albumDeta
        }
    }
    fun getCollectorDetail(albumId: Int) : CollectorDetail{
        return if (collectorDetail.containsKey(albumId)) collectorDetail[albumId]!! else CollectorDetail(0,"","","",
            emptyList<Comment>(),emptyList<Performer>())
    }

    //Lista de Albumes
    private var albums: HashMap<Int, List<Album>> = hashMapOf()
    fun addAlbums(albumId: Int, albumes: List<Album>){
        if (!albums.containsKey(albumId)){
            albums[albumId] = albumes
        }
    }
    fun getAlbums(albumId: Int) : List<Album>{
        return if (albums.containsKey(albumId)) albums[albumId]!! else listOf<Album>()
    }

    //Lista de CollectorAlbumes
    private var collectors: HashMap<Int, List<Collector>> = hashMapOf()
    fun addCollectors(collectorId: Int, collectores: List<Collector>){
        if (!collectors.containsKey(collectorId)){
            collectors[collectorId] = collectores
        }
    }
    fun getCollectors(collectorId: Int) : List<Collector>{
        return if (collectors.containsKey(collectorId)) collectors[collectorId]!! else listOf<Collector>()
    }




}

