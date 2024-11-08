package com.example.juegopicobotellag8.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Retos(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String): Serializable
