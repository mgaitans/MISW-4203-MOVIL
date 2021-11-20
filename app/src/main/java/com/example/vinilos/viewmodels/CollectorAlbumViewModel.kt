package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.repositories.AlbumDetailRepository
import com.example.vinilos.repositories.CollectorRepository
import org.json.JSONObject

class CollectorAlbumViewModel (application: Application, collectorId: Int, albumId: ArrayList<Album>, price: String, status:String) :  AndroidViewModel(application){

    private val collectorAlbumRepository = CollectorRepository(application)

    private val _collectorAlbum = MutableLiveData<JSONObject>()

    val collectorAlbum: LiveData<JSONObject>
        get() = _collectorAlbum

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val idCollector:Int = collectorId
    val idAlbum:ArrayList<Album> = albumId
    val postParams = mapOf<String, Any>(
        "price" to price,
        "status" to status
    )
    val postBody = JSONObject(postParams)
    init {
        postDataFromNetwork()
    }



    private fun postDataFromNetwork() {
        for(i in 0 until idAlbum.size){
            collectorAlbumRepository.addAlbum(

                idCollector,idAlbum[i].albumId,postBody,{
                    _collectorAlbum.postValue(it)
                    _eventNetworkError.value = false
                    _isNetworkErrorShown.value = false
                },{

                    _eventNetworkError.value = true
                }


            )
        }

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application,val collectorId: Int, val albumId: ArrayList<Album>, val price: String, val status: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorAlbumViewModel(app, collectorId,albumId,price,status) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}