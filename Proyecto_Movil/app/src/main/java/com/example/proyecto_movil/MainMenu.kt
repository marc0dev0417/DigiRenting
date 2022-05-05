package com.example.proyecto_movil

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu: AppCompatActivity(){

    private lateinit var bottomNav:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        var imageView: ImageView? = findViewById(R.id.imageIconAddHouse)

        imageView?.setOnClickListener {

            Toast.makeText(this, "Has hecho click en la imagen", Toast.LENGTH_SHORT).show()

        }

        Log.d("idReceiver", intent.getIntExtra("userId", 0).toString())

        supportFragmentManager.beginTransaction().replace(R.id.frament_container,
            HomeFragment()
        ).commit()
        bottomNav.setOnItemSelectedListener {
            var selectedFragment:Fragment? = null
            when(it.itemId){
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_favorites -> selectedFragment = FavoritesFrament()
                R.id.nav_search -> selectedFragment = SearchFrament()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frament_container,
                selectedFragment!!
            ).commit()
            return@setOnItemSelectedListener true
        }
    }
}