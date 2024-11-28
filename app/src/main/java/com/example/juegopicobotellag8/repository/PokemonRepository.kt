package com.example.juegopicobotellag8.repository

import com.example.juegopicobotellag8.api.ApiService
import com.example.juegopicobotellag8.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
) {

    // Función para obtener la lista de Pokémon
    suspend fun getPokemons(): MutableList<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemons()
                response.pokemonList
            } catch (e: Exception) {
                e.printStackTrace()
                mutableListOf() // Devuelve una lista vacía en caso de error
            }
        }
    }
}
