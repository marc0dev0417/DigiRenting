package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.model.User
import com.google.gson.Gson

class LoginActivity : AppCompatActivity() {

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var buttonLogin: Button
    private lateinit var buttonCancel: Button
    private lateinit var fieldMail : EditText
    private lateinit var fieldPassword : EditText

    private val url = "http://192.168.1.34/users"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin = findViewById(R.id.login_Login)
        buttonCancel = findViewById(R.id.cancel_Login)
        fieldMail = findViewById(R.id.mail_Login)
        fieldPassword = findViewById(R.id.password_Login)

        buttonLogin.setOnClickListener {

            findByMail()

        }

        buttonCancel.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

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

                if (mail == user!!.mail && password == user.password) {

                    Log.d("responseUser", "User is correct")
                    Toast.makeText(this, "Login correcto", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HasEntradoActivity::class.java)
                    startActivity(intent)

                } else {

                    Log.d("responseUser", "User is not correct")
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_LONG).show()
                    fieldMail.setText("")
                    fieldPassword.setText("")

                }

            }, {

                    error -> Log.d("responseMessage", error.toString())

            }) {

        }

        mRequestQueue.add(stringRequest)

    }

}