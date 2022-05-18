package com.example.proyecto_movil.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyecto_movil.LoginActivity
import com.example.proyecto_movil.MainMenu
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken

class ProfileFragment : Fragment(){

    //Utils =>
    private lateinit var dataBaseSql: ManagerToken
    private var listUserSql: MutableList<UserDataSQL> = mutableListOf()
    private var userProfile = UserDataSQL()

    //Components =>
    private lateinit var imageViewProfile: ImageView
    private lateinit var buttonCloseSession: Button

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

        dataBaseSql = ManagerToken(context)

        var index = (activity as MainMenu).idIndex

        listUserSql = dataBaseSql.viewUserWithToken()

      //  listUserSql.removeAt(0)
        listUserSql.filter { item -> item.idUser == index}

        userProfile = listUserSql[0]

        Log.d("profileUsername", userProfile.username.toString())
        imageViewProfile = view.findViewById(R.id.ivProfile)

        tvProfilePresentation = view.findViewById(R.id.tvProfilePresentation)
        tvUsername = view.findViewById(R.id.tvUsername)
        tvMail = view.findViewById(R.id.tvMail)
        tvAddress = view.findViewById(R.id.tvAddress)
        tvPassword = view.findViewById(R.id.tvPassword)

        buttonCloseSession = view.findViewById(R.id.button_cerrar_sesion)

        buttonCloseSession.setOnClickListener {
            dataBaseSql.deleteUserWithToken(userProfile.idUser!!)
            startActivity(Intent(context, LoginActivity::class.java))
        }

        imageViewProfile.setImageResource(R.drawable.ic_baseline_person_24)


        tvProfilePresentation.text = "Welcome to your data personal ${userProfile.firstname} ${userProfile.lastname}"
        tvUsername.text = userProfile.username
        tvMail.text = userProfile.mail
        tvAddress.text = userProfile.address
        tvPassword.text = userProfile.password

    }
}