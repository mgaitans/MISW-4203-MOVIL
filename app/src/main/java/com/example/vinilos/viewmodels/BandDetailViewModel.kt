package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.AlbumDetail
import com.example.vinilos.models.Band
import com.example.vinilos.repositories.AlbumDetailRepository
import com.example.vinilos.repositories.PerformersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BandDetailViewModel (application: Application, bandId: Int) :  AndroidViewModel(application)  {


    private val bandDetailRepository = PerformersRepository(application)

    private val _albumDetail = MutableLiveData<Band>()

    val albumDetail: LiveData<Band>
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
                    var data = bandDetailRepository.getDetailBand(id)
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
            if (modelClass.isAssignableFrom(BandDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BandDetailViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}