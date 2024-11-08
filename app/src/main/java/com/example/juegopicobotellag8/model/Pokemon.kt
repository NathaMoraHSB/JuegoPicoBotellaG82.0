package com.example.juegopicobotellag8.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val id: Int,

    @SerializedName("img")
    val image: String,
)