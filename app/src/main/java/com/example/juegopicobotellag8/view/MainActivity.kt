package com.example.juegopicobotellag8.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.juegopicobotellag8.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
