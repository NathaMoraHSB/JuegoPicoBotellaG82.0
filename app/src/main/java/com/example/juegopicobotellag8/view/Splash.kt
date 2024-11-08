package com.example.juegopicobotellag8.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.juegopicobotellag8.R
import com.example.juegopicobotellag8.databinding.SplashBinding
import com.example.juegopicobotellag8.view.fragment.HomeFragment


class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        var handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }, 5000)
    }
}