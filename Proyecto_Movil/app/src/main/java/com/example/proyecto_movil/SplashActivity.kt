package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)
        setTheme(R.style.Theme_digi_renting)
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, LoginActivity::class.java))

    }

}