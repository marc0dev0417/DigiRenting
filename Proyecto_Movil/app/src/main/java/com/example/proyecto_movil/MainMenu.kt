package com.example.proyecto_movil

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu: AppCompatActivity(){

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var topNav: BottomNavigationView
    private lateinit var titleFragment: String

    var idUser: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_main)

        bottomNav = findViewById(R.id.bottom_navigation)
        topNav = findViewById(R.id.top_navigation)


        Log.d("idReceiver", intent.getIntExtra("userId", 0).toString())

        idUser = intent.getIntExtra("userId", 0)

        supportFragmentManager.beginTransaction().replace(R.id.frament_container,
            HomeFragment()
        ).commit()
        bottomNav.setOnItemSelectedListener {
            var selectedFragment: Fragment? = null
            when(it.itemId){
                R.id.nav_home -> selectedFragment = HomeFragment()
                R.id.nav_favorites -> selectedFragment = FavoritesFragment()
                R.id.nav_search -> selectedFragment = AddFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frament_container,
                selectedFragment!!
            ).commit()
            return@setOnItemSelectedListener true
        }
        topNav.setOnItemSelectedListener {
            var selectedFragment: Fragment? = null

            when(it.itemId){
                R.id.nav_profile -> selectedFragment = ProfileFragment()
                R.id.nav_delete_house -> selectedFragment = RemoveHouseFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frament_container, selectedFragment!!).commit()
            return@setOnItemSelectedListener true
        }
    }
}