package com.example.proyecto_movil.model

data class UserDataSQL(
    var idUser: Int? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var username: String? = null,
    var mail: String? = null,
    var address: String? = null,
    var password: String? = null,
    var token: String? = null,
    var tokenExpired: String? = null
)
