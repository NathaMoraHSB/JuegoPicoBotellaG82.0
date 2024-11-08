package com.example.juegopicobotellag8.model

import com.google.gson.annotations.SerializedName

data class ListPokemon(
    @SerializedName("pokemon")
    val pokemonList: MutableList<Pokemon>,
)