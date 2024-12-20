package com.example.juegopicobotellag8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegopicobotellag8.model.Retos
import com.example.juegopicobotellag8.repository.RetosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetosViewModel @Inject constructor(
    private val retosRepository: RetosRepository
) : ViewModel() {

    private val _listRetos = MutableLiveData<MutableList<Retos>>()
    val listRetos: LiveData<MutableList<Retos>> get() = _listRetos

    fun saveRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.saveRetos(retos,
                onSuccess = {
                    println("Reto agregado correctamente")
                },
                onFailure = { exception ->
                    println("Error al agregar el reto: $exception")
                })
        }
    }

    fun getListRetos() {
        retosRepository.getListRetos().observeForever { retos ->
            _listRetos.value = retos
        }
    }

    fun updateRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.updateRepositoy(retos,
                onSuccess = {
                    println("Reto actualizado correctamente")
                },
                onFailure = { exception ->
                    println("Error al actualizado el reto: $exception")
                })
        }
    }

    fun deleteRetos(retos: Retos) {
        viewModelScope.launch {
            retosRepository.deleteRetos(retos,
                onSuccess = {
                    println("Reto eliminado correctamente")
                },
                onFailure = { exception ->
                    println("Error al eliminado el reto: $exception")
                })
        }
    }
}