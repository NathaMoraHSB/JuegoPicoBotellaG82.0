package com.example.juegopicobotellag8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegopicobotellag8.model.Pokemon
import com.example.juegopicobotellag8.repository.PokemonRepository
import com.example.juegopicobotellag8.repository.RetosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonViewModel (application: Application): AndroidViewModel(application) {

    val context = getApplication<Application>()
    private val PokemonRepository = PokemonRepository(application)

    private val _list_pokemon = MutableLiveData<MutableList<Pokemon>>()
    val list_pokemon: LiveData<MutableList<Pokemon>> = _list_pokemon

    fun getPokemons() {
        viewModelScope.launch {
            try {
                _list_pokemon.value = PokemonRepository.getPokemons()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}