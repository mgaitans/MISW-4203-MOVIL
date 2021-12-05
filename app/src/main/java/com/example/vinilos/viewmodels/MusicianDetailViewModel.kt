package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*

import com.example.vinilos.models.Musician
import com.example.vinilos.repositories.PerformersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MusicianDetailViewModel (application: Application, bandId: Int) :  AndroidViewModel(application){


    private val musicianDetailRepository = PerformersRepository(application)

    private val _albumDetail = MutableLiveData<Musician>()

    val albumDetail: LiveData<Musician>
        get() = _albumDetail

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    val id:Int = bandId

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try{
            viewModelScope.launch (Dispatchers.Default){
                withContext(Dispatchers.IO){
                    var data = musicianDetailRepository.getDetailMusician(id)
                    _albumDetail.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch(e:Exception){
            _eventNetworkError.value = true
        }

    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MusicianDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MusicianDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}