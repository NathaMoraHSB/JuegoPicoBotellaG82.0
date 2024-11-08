package com.example.juegopicobotellag8.repository

import android.content.Context
import com.example.juegopicobotellag8.api.ApiService
import com.example.juegopicobotellag8.model.Pokemon
import com.example.juegopicobotellag8.utils.Constants.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository (context: Context) {
    private val apiService: ApiService

    init {
        // Configura Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL) // Reemplaza con la URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun getPokemons() : MutableList<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPokemons()
                response.pokemonList
            } catch (e: Exception) {
                e.printStackTrace()
                mutableListOf()
            }
        }
    }
}