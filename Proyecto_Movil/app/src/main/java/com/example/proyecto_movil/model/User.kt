package com.example.proyecto_movil.model

data class User(

    var idUser:Int? = null,
    var firstname:String,
    var lastname:String,
    var address:String,
    var username:String,
    var mail:String,
    var password:String,
    var houses: MutableList<House>? = null,
    var houseLikes: MutableList<House>? = null

)
