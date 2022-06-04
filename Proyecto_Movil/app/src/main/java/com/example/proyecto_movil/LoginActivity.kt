package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.model.Token
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LoginActivity : AppCompatActivity() {

    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignup: Button
    private lateinit var fieldUsername : EditText
    private lateinit var fieldPassword : EditText

    //SQLite =>
    private lateinit var dataBaseSql: ManagerToken
    private var listUserSql: MutableList<UserDataSQL> = mutableListOf()
    private var userProfile = UserDataSQL()

    val url = "http://192.168.1.128:8080/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var dateFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM)
        var currentDate = dateFormatMedium.format(Date())

        var indexRegisterUser = SignupActivity().indexRegister

        dataBaseSql = ManagerToken(applicationContext)

        buttonLogin = findViewById(R.id.bLogin_Login)
        buttonSignup = findViewById(R.id.bSignup_Login)
        fieldUsername = findViewById(R.id.etUsername_login)
        fieldPassword = findViewById(R.id.etPassword_Login)


        buttonLogin.setOnClickListener {
            findUser()
        }

        buttonSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()


        var dateFormatMedium = DateFormat.getDateInstance(DateFormat.MEDIUM)
        var currentDate = dateFormatMedium.format(Date())

        listUserSql = dataBaseSql.viewUserWithToken()

        listUserSql.filter { item -> item.username ==  fieldUsername.toString()}

        if(listUserSql.isNotEmpty()) {
            userProfile = listUserSql[0]
            if (userProfile.token != null) {
                var dateExpiredToken = dateFormatMedium.parse(userProfile.tokenExpired)
                var dateSystem = dateFormatMedium.parse(currentDate)
                if (dateSystem.before(dateExpiredToken)) {
                    val intent = Intent(this, MainMenu::class.java)
                    startActivity(intent)
                } else {
                    dataBaseSql.deleteUserWithToken(userProfile.idUser!!)
                }
            }
        }
    }
    fun goingLogin(){
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }
    private fun findUser() {

        val username = fieldUsername.text.toString()
        val password = fieldPassword.text.toString()

        gson = Gson()

        mRequestQueue = Volley.newRequestQueue(this)

        val stringRequest = object: StringRequest(
            Method.GET, "$url?username=$username&password=$password",
            {

                    response ->  Log.d("responseMessage", response)

                val tokenUser: Token? = gson.fromJson(response, Token::class.java)

               val resultVerify = BCrypt.verifyer().verify(password.toCharArray(), tokenUser?.user?.password)

                Log.d("passwordE", resultVerify.verified.toString())

                if (username == tokenUser?.user?.username && resultVerify.verified) {

                    Log.d("responseUser", "User is correct")
                    dataBaseSql.addUserWithToken(tokenUser?.user?.idUser!!.toInt(), tokenUser.token!!, tokenUser.expired_date!!,tokenUser?.user?.firstname!!, tokenUser?.user?.lastname!!,tokenUser?.user?.username!!, tokenUser?.user?.mail!!, tokenUser?.user?.address!!, tokenUser?.user?.password!!)

                    Toast.makeText(applicationContext, "Login correcto", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, MainMenu::class.java)
                    intent.putExtra("indexUser", tokenUser?.user?.idUser)
                    startActivity(intent)

                } else {

                    Log.d("responseUser", "User is not correct")
                    Toast.makeText(applicationContext, "Login incorrecto", Toast.LENGTH_LONG).show()
                    fieldUsername.setText("")
                    fieldPassword.setText("")

                }

            }, {
                    Log.d("responseMessage", it.toString())
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
            }) {

        }
        stringRequest.retryPolicy = DefaultRetryPolicy(20000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        mRequestQueue.add(stringRequest)

    }
}