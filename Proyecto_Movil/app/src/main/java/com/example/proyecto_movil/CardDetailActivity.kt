package com.example.proyecto_movil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.squareup.picasso.Picasso


class CardDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        val dataIntent = intent

        val url1 = dataIntent.getStringExtra("intentUrl")
        val url2 = dataIntent.getStringExtra("intentUrlSecond")
        val region = dataIntent.getStringExtra("intentRegion")
        val address = dataIntent.getStringExtra("intentAddress")
        val price = dataIntent.getDoubleExtra("intentPrice", 0.0)
        val space = dataIntent.getIntExtra("intentSpace", 0)
        val mail = dataIntent.getStringExtra("intentMail")

        val url1Screen = findViewById<ImageView>(R.id.ivLeft_Detail)
        val url2Screen = findViewById<ImageView>(R.id.ivRight_Detail)
        val regionScreen = findViewById<TextView>(R.id.tvRegion_Detail)
        val addressScreen = findViewById<TextView>(R.id.tvAddress_Detail)
        val priceScreen = findViewById<TextView>(R.id.tvPrice_Detail)
        val spaceScreen = findViewById<TextView>(R.id.tvSpace_Detail)
        val mailScreen = findViewById<TextView>(R.id.tvMail_Detail)

        Picasso.get().load(url1).into(url1Screen)
        Picasso.get().load(url2).into(url2Screen)
        regionScreen.text = "Region: $region"
        addressScreen.text = "Dirección: $address"
        priceScreen.text = "Precio: $price"
        spaceScreen.text = "Metros: $space m2"
        mailScreen.text = "Mail: $mail"

    }
}