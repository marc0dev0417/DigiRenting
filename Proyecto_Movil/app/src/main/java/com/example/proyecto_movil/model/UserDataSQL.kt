package com.example.proyecto_movil.model

data class UserDataSQL(
    var idUser: Int? = null,
    var username: String? = null,
    var token: String? = null,
    var tokenExpired: String? = null
)
