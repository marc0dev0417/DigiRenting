package com.example.proyecto_movil.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import at.favre.lib.crypto.bcrypt.BCrypt
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.LoginActivity
import com.example.proyecto_movil.MainMenu
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.Token
import com.example.proyecto_movil.model.User
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.model.UserUpdate
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.gson.Gson


class ProfileFragment : Fragment(){

    //Utils =>
    private lateinit var dataBaseSql: ManagerToken
    private var listUserSql: MutableList<UserDataSQL> = mutableListOf()
    private var userProfile = UserDataSQL()
    private lateinit var gson: Gson
    private lateinit var mRequestQueue: RequestQueue

    //Components =>
    private lateinit var imageViewProfile: ImageView
    private lateinit var buttonCloseSession: Button
    private lateinit var buttonSaveUser: Button

    private lateinit var tvProfilePresentation: TextView
    private lateinit var tvUsername: TextView
    private lateinit var tvMail: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvPassword: TextView



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gson = Gson()


        dataBaseSql = ManagerToken(context)

        var index = (activity as MainMenu).idIndex

        listUserSql = dataBaseSql.viewUserWithToken()

      //  listUserSql.removeAt(0)
        listUserSql.filter { item -> item.idUser == index}

        userProfile = listUserSql[0]

        val url = "http://192.168.1.36:8080/users/update/${userProfile.idUser}"


        Log.d("profileUsername", userProfile.username.toString())
        imageViewProfile = view.findViewById(R.id.ivProfile)

        tvProfilePresentation = view.findViewById(R.id.tvProfilePresentation)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvMail = view.findViewById(R.id.tvMail)
        tvAddress = view.findViewById(R.id.tvAddress)
        tvPassword = view.findViewById(R.id.tvPassword)

        buttonCloseSession = view.findViewById(R.id.button_cerrar_sesion)
        buttonSaveUser = view.findViewById(R.id.button_save_user)

        buttonCloseSession.setOnClickListener {
            dataBaseSql.deleteUserWithToken(userProfile.idUser!!)
            startActivity(Intent(context, LoginActivity::class.java))
        }

        imageViewProfile.setImageResource(R.drawable.ic_baseline_person_24)

        tvProfilePresentation.text = "Welcome to your data personal ${userProfile.firstname} ${userProfile.lastname}"
        tvUsername.text = userProfile.username
        tvMail.text = userProfile.mail
        tvAddress.text = userProfile.address
        tvPassword.text = ""


        buttonSaveUser.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Enter your old password")

            val input = EditText(context)

            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            builder.setView(input)


            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->

                    //Toast.makeText(context, input.text, Toast.LENGTH_SHORT).show()
                    val resultVerify = BCrypt.verifyer().verify(input.text.toString().toCharArray(), userProfile.password)

                    if(resultVerify.verified){
                        Log.d("urlProfile", url +"  ${userProfile.token}")
                        Toast.makeText(context, "user inserted", Toast.LENGTH_SHORT).show()
                        val user = UserUpdate(
                            username = tvUsername.text.toString(),
                            address = tvAddress.text.toString(),
                            mail = tvMail.text.toString(),
                            password = tvPassword.text.toString()
                        )
                        val jsonUser = gson.toJson(user, UserUpdate::class.java)

                        mRequestQueue = Volley.newRequestQueue(context)

                        val stringRequest = object: StringRequest(
                            Method.PUT, url,
                            {

                            var token = gson.fromJson(it, Token::class.java)

                                dataBaseSql.deleteUserWithToken(userProfile.idUser!!)
                            dataBaseSql.addUserWithToken(
                                token.user?.idUser!!,
                                token.token!!,
                                token.expired_date!!,
                                token.user?.firstname!!,
                                token.user?.lastname!!,
                                token.user!!.username!!,
                                token.user!!.mail!!,
                                token.user!!.address!!,
                                token.user!!.password!!
                            )
                                Log.d("userTokenProfile", token.user!!.username.toString())
                                //startActivity(Intent(context, LoginActivity::class.java))

                            }, {
                                Log.d("Fprofile", it.toString())
                            }) {
                            override fun getBodyContentType(): String {
                                return "application/json"
                            }
                            override fun getBody(): ByteArray {
                                return jsonUser.toByteArray()
                            }
                            override fun getHeaders(): MutableMap<String, String> {
                                val accessTokenApi: HashMap<String, String> = HashMap()

                                accessTokenApi["Authorization"] = "Bearer ${userProfile.token}"

                                return accessTokenApi
                            }
                        }
                        mRequestQueue.add(stringRequest)
                    }else{
                        Toast.makeText(context, "password incorrect", Toast.LENGTH_SHORT).show()
                    }

                })
            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()
            }
    }
}