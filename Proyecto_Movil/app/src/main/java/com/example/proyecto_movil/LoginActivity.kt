package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.model.User
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignup: Button
    private lateinit var fieldMail : EditText
    private lateinit var fieldPassword : EditText

    val url = "http://192.168.1.138:8080/users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.bLogin_Login)
        buttonSignup = findViewById(R.id.bSignup_Login)
        fieldMail = findViewById(R.id.etEmail_Login)
        fieldPassword = findViewById(R.id.etPassword_Login)

        buttonLogin.setOnClickListener {

            findByMail()

        }

        buttonSignup.setOnClickListener {

            startActivity(Intent(this, SignupActivity::class.java))

        }

    }

    fun findByMail() {

        val mail = fieldMail.text.toString()
        val password = fieldPassword.text.toString()

        gson = Gson()

        mRequestQueue = Volley.newRequestQueue(this)

        val stringRequest = object: StringRequest(
            Method.GET, "$url?mail=$mail",
            {

                    response ->  Log.d("responseMessage", response)

                val user: User? = gson.fromJson(response, User::class.java)

                if (mail == user?.mail && password == user.password) {

                    Log.d("responseUser", "User is correct")
                    Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, MainMenu::class.java)
                    intent.putExtra("userId", user.idUser)
                    startActivity(intent)

                } else {

                    Log.d("responseUser", "User is not correct")
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_LONG).show()
                    fieldMail.setText("")
                    fieldPassword.setText("")

                }

            }, {

                    Log.d("responseMessage", it.toString())
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT)

            }) {

        }
        stringRequest.retryPolicy = DefaultRetryPolicy(20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        mRequestQueue.add(stringRequest)

    }
}