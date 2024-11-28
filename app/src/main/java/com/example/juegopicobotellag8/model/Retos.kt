package com.example.juegopicobotellag8.model

import java.io.Serializable
import com.google.firebase.Timestamp

data class Retos(
    var id: String = "",
    val description: String = "",
    var created_at: Timestamp = Timestamp.now()
) : Serializable
