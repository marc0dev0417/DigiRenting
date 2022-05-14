package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.model.User
import com.google.android.material.slider.Slider
import com.google.gson.Gson

class SignupActivity : AppCompatActivity() {

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var buttonCreateUser: Button
    private lateinit var buttonBackToLogin: Button

    private lateinit var fieldMail : EditText
    private lateinit var fieldUsername : EditText

    private lateinit var fieldFirstName : EditText
    private lateinit var fieldLastName : EditText
    private lateinit var fieldAddress : EditText
    private lateinit var fieldPassword : EditText

    val url = "http://192.168.1.36:8080/register"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        buttonCreateUser = findViewById(R.id.bSignup_Signup)
        buttonBackToLogin = findViewById(R.id.bBackLogin_Signup)

        fieldMail = findViewById(R.id.etEmail_Signup)
        fieldUsername = findViewById(R.id.etUsername_Signup)

        fieldFirstName = findViewById(R.id.etFirstName_Signup)
        fieldLastName = findViewById(R.id.etLastName_Signup)
        fieldAddress = findViewById(R.id.etAddress_Signup)
        fieldPassword = findViewById(R.id.etPassword_Signup)


        buttonCreateUser.setOnClickListener { addUser() }

        buttonBackToLogin.setOnClickListener { startActivity(Intent(this, LoginActivity::class.java)) }

    }

    fun addUser() {

        mRequestQueue = Volley.newRequestQueue(this)
        gson = Gson()

        val user = User(null,
            fieldFirstName.text.toString(),
            fieldLastName.text.toString(),
            fieldAddress.text.toString(),
            fieldUsername.text.toString(),
            fieldMail.text.toString(),
            fieldPassword.text.toString(),
            houses = null,
            houseLikes = null
        )

        val stringJson = gson.toJson(user, User::class.java)
        val stringRequest = object: StringRequest(Method.POST, url,
            {
                    Log.d("responseMessage", it)
                    var intent = Intent(this, SliderActivity::class.java)
                    startActivity(intent)

            }, {
                    Log.d("responseMessage", it.toString())
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            }) {

            override fun getBodyContentType(): String {
                return "application/json"
            }

            override fun getBody(): ByteArray {
                return stringJson.toByteArray()
            }
        }
        mRequestQueue!!.add(stringRequest!!)
    }
}