package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.proyecto_movil.fragment.*
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.DateFormat
import java.util.*

class MainMenu: AppCompatActivity(){

    private lateinit var bottomNav: me.ibrahimsn.lib.SmoothBottomBar
    private lateinit var topNav: BottomNavigationView
    private lateinit var dataBaseSql: ManagerToken

    private var listUserSql: MutableList<UserDataSQL> = mutableListOf()

    var idIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_main)

        bottomNav = findViewById(R.id.bottomBar)
        topNav = findViewById(R.id.top_navigation)

        dataBaseSql = ManagerToken(applicationContext)
        idIndex = intent.getIntExtra("indexUser", 0)

        Log.d("userDatabase", dataBaseSql.viewUserWithToken()[0].token.toString())
        Log.d("indexMenu", idIndex.toString())

        supportFragmentManager.beginTransaction().replace(R.id.frament_container,
            HomeFragment()
        ).commit()
        bottomNav.setOnItemSelectedListener {
            var selectedFragment: Fragment? = null
            when(it){
                0 -> selectedFragment = HomeFragment()
                1 -> selectedFragment = FavoritesFragment()
                2 -> selectedFragment = AddFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.frament_container,
                selectedFragment!!
            ).commit()
            return@setOnItemSelectedListener
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
    override fun onResume() {
        super.onResume()

        listUserSql = dataBaseSql.viewUserWithToken()

        if(listUserSql.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}