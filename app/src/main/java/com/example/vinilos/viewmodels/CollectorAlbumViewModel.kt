package com.example.vinilos.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.repositories.AlbumDetailRepository
import com.example.vinilos.repositories.CollectorRepository
import org.json.JSONObject

class CollectorAlbumViewModel (application: Application, collectorId: Int, albumId: Int, price: Double, status:String) :  AndroidViewModel(application){

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
    val idAlbum:Int = albumId
    val postParams = mapOf<String, Any>(
        "price" to price,
        "status" to status
    )
    val postBody = JSONObject(postParams)
    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        collectorAlbumRepository.addAlbum(
            idCollector,idAlbum,postBody,{
                _collectorAlbum.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },{

                _eventNetworkError.value = true
            }


        )
    }

    private fun postDataFromNetwork() {
        collectorAlbumRepository.addAlbum(
            idCollector,idAlbum,postBody,{
                _collectorAlbum.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            },{

                _eventNetworkError.value = true
            }


        )
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}