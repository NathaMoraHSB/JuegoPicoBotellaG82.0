package com.example.juegopicobotellag8.api

import com.example.juegopicobotellag8.model.ListPokemon
import com.example.juegopicobotellag8.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getPokemons(): ListPokemon
}