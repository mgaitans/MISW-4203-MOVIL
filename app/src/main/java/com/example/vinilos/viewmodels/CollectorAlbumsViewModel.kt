package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.CollectorAlbums
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.repositories.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CollectorAlbumsViewModel (application: Application, collectorId: Int) :  AndroidViewModel(application){

    private val collectorRepository = CollectorRepository(application)

    private val _albums = MutableLiveData<List<CollectorAlbums>>()

    val albums: LiveData<List<CollectorAlbums>>
        get() = _albums

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = collectorId
    init {
        refreshCollectorAlbumsFromNetwork()
    }

    private fun refreshCollectorAlbumsFromNetwork() {
        try {
        viewModelScope.launch (Dispatchers.Default){
            withContext(Dispatchers.IO){
                var data = collectorRepository.getAlbums(id)
                _albums.postValue(data)
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

    class Factory(val app: Application, val collectorId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorAlbumsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorAlbumsViewModel(app,collectorId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}