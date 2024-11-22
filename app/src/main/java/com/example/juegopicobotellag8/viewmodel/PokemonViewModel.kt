package com.example.juegopicobotellag8.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juegopicobotellag8.model.Pokemon
import com.example.juegopicobotellag8.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _listPokemon = MutableLiveData<MutableList<Pokemon>>()
    val listPokemon: LiveData<MutableList<Pokemon>> = _listPokemon

    fun getPokemons() {
        viewModelScope.launch {
            try {
                _listPokemon.value = pokemonRepository.getPokemons()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}