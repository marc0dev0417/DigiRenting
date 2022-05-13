package com.example.proyecto_movil.model

data class Token(
    var error: Boolean? = null,
    var message: String? = null,
    var token: String? = null,
    var token_expired: Boolean? = null,
    var expired_date: String? = null
)
