package com.example.vinilos.viewmodels

import android.app.Application

import androidx.lifecycle.*

import com.example.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AlbumCreateViewModel (application: Application, name: String, cover: String, releaseDate:String, description: String, genre: String, recordLabel: String) :  AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)

    private val _createAlbum = MutableLiveData<JSONObject>()

    val createAlbum: LiveData<JSONObject>
        get() = _createAlbum

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val postParams = mapOf<String, Any>(
        "name" to name,
        "cover" to cover,
        "releaseDate" to releaseDate,
        "description" to description,
        "genre" to genre,
        "recordLabel" to recordLabel
    )
    val postBody = JSONObject(postParams)
    init {
        postDataFromNetwork()
    }

    private fun postDataFromNetwork() {

        try {
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data =albumRepository.createAlbum(postBody)
                    _createAlbum.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.value = true
        }


    }
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val name: String, val cover: String, val releaseDate:String, val description: String, val genre: String, val recordLabel: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app, name, cover, releaseDate, description, genre, recordLabel) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}