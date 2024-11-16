package com.example.juegopicobotellag8.repository

import android.content.Context
import com.example.juegopicobotellag8.api.ApiService
import com.example.juegopicobotellag8.model.Pokemon
import com.example.juegopicobotellag8.utils.Constants.BASE_URL
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    suspend fun getPokemons(): MutableList<Pokemon> {
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