package com.example.juegopicobotellag8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.repository.RetosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetosViewModel @Inject constructor(
    application: Application,
    private val retosRepository: RetosRepository
) : AndroidViewModel(application) {

    private val _listRetos = MutableLiveData<MutableList<Retos>>()
    val listRetos: LiveData<MutableList<Retos>> get() = _listRetos

    fun saveRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.saveRetos(retos)
            _listRetos.value = retosRepository.getListRetos()
        }
    }

    fun getListRetos() {
        viewModelScope.launch {
            _listRetos.value = retosRepository.getListRetos()
        }
    }

    fun deleteRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.deleteRetos(retos)
            _listRetos.value = retosRepository.getListRetos()
        }
    }

    fun updateRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.updateRepositoy(retos)
            _listRetos.value = retosRepository.getListRetos()
        }
    }
}