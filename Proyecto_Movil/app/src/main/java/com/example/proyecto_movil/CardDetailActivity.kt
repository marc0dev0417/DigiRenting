package com.example.proyecto_movil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val dataIntent = intent

        val price = dataIntent.getDoubleExtra("intentPrice", 0.0)
        Log.d("price", price.toString())   //ASI CON EL RESTO IKER PARA OBTENER LOS DATOS CON ESTO PODES ARMAR EL DISEÑO

    }
}