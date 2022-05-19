package com.example.proyecto_movil.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.proyecto_movil.MainMenu
import com.example.proyecto_movil.R
import com.example.proyecto_movil.model.House
import com.example.proyecto_movil.model.Image
import com.example.proyecto_movil.model.UserDataSQL
import com.example.proyecto_movil.sqltoken.ManagerToken
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.*
import com.google.gson.Gson
import java.io.File

class AddFragment : Fragment() {

    private lateinit var dataBaseSql: ManagerToken

    //List =>

    private val listImage: MutableList<Image> = mutableListOf()
    private var listUserSql: MutableList<UserDataSQL> = mutableListOf()

    //Buttons =>
    private lateinit var buttonChoose: Button
    private lateinit var buttonUpload: Button

    //ImageViews =>
    private lateinit var imageViewFirst: ImageView
    private lateinit var imageViewSecond: ImageView
    private lateinit var imageViewThird: ImageView

    //Fields =>
    private lateinit var regionEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var spaceEditText: EditText
    private lateinit var descriptionEditText: EditText

    //Counts to controller the state in Button Choose =>
    private var count = 0

    //Utils to firebase =>
    private lateinit var storageReference: StorageReference
    private lateinit var uploadTask: UploadTask
    private lateinit var fileImage: File

    //API =>
    private lateinit var mRequestQueue: RequestQueue
    private lateinit var gson: Gson
    private lateinit var url: String
    private  var userProfile: UserDataSQL = UserDataSQL()

    //List to save operations in Firebase and internal storage android =>
    private val listUriImage: MutableList<Uri> = mutableListOf()
    private val listUrl: HashSet<String> = HashSet()



    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        dataBaseSql = ManagerToken(context)

        val index: Int = (activity as MainMenu).idIndex

        Log.d("indexAdd", index.toString())
        listUserSql = dataBaseSql.viewUserWithToken()

        listUserSql.map {
            Log.d("userList", "${it.username.toString()} index ${it.idUser.toString()}")
            userProfile = it
        }


        Log.d("tokenUser", userProfile.token.toString())

        //Client of Firebase =>
        storageReference = FirebaseStorage.getInstance().reference

        //Button add house =>
        buttonChoose = view.findViewById(R.id.button_choose)
        buttonUpload = view.findViewById(R.id.button_upload)

        //Images view put reference id =>
        imageViewFirst = view.findViewById(R.id.image_view_first)
        imageViewSecond = view.findViewById(R.id.image_view_second)
        imageViewThird = view.findViewById(R.id.image_view_third)

        //Input fields add house =>
        regionEditText = view.findViewById(R.id.region_add_house)
        addressEditText = view.findViewById(R.id.address_add_house)
        priceEditText = view.findViewById(R.id.price_add_house)
        spaceEditText = view.findViewById(R.id.space_add_house)
        descriptionEditText = view.findViewById(R.id.description_add_house)

        buttonUpload.setOnClickListener {
            url = "http://192.168.87.192:8080/users/${userProfile.idUser}"
            var house: House

    if(validationFields()){
            for (uriImage: Uri in listUriImage) {

                fileImage = File(uriImage.path)

                uploadTask =
                    storageReference.child("houses_images/${fileImage.name}").putFile(uriImage)

                uploadTask.addOnSuccessListener {

                    val firebaseUri: Task<Uri> = it.storage.downloadUrl

                    firebaseUri.addOnSuccessListener { uri ->
                        listImage.add(Image(null, uri.toString()))
                    }
                }
        }
        uploadTask =
            storageReference.child("houses_images/${fileImage.name}").putFile(listUriImage[2])
            uploadTask.addOnCompleteListener {

                Toast.makeText(context, "Termino la tarea ${listImage.size}", Toast.LENGTH_SHORT)
                    .show()

                house = House(
                    address = addressEditText.text.toString(),
                    region = regionEditText.text.toString(),
                    price = priceEditText.text.toString().toDouble(),
                    description = descriptionEditText.text.toString(),
                    space = spaceEditText.text.toString().toInt(),
                    images = listImage
                )

                mRequestQueue = Volley.newRequestQueue(context)
                gson = Gson()

                val stringJson = gson.toJson(house, House::class.java)
                val stringRequest = object : StringRequest(
                    Method.PUT, url,
                    {
                        Toast.makeText(context, "Se ha añadido", Toast.LENGTH_SHORT).show()
                    }, {
                        Toast.makeText(context, "No se ha añadido", Toast.LENGTH_SHORT).show()
                    }) {

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }

                    override fun getBody(): ByteArray {
                        return stringJson.toByteArray()
                    }

                    override fun getHeaders(): MutableMap<String, String> {
                        val accessTokenApi: HashMap<String, String> = HashMap()

                        accessTokenApi["Authorization"] = "Bearer ${userProfile.token}"

                        return accessTokenApi
                    }

                }
                mRequestQueue.add(stringRequest)

            }

            }else{
                Toast.makeText(context, "Not uploaded", Toast.LENGTH_LONG).show()
                buttonChoose.isEnabled = true

                count = 0
    }
    }
        buttonChoose.setOnClickListener {
            count++

            when(count){
                1 -> {
                    selectedImage()
                }
                2 -> {
                    selectedImage()
                }
                3 -> {
                    selectedImage()
                }
            }
        }
    }
    private fun selectedImage() {
        getResult.launch("image/*")
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.GetContent()) {

        var imageUri = it

        if(imageUri == null){
            count--
        }else{
            when(count){
                1 -> {
                    imageViewFirst.setImageURI(imageUri)
                    listUriImage.add(imageUri)
                }
                2 -> {
                    imageViewSecond.setImageURI(imageUri)
                    listUriImage.add(imageUri)
                }
                3 -> {
                    imageViewThird.setImageURI(imageUri)
                    listUriImage.add(imageUri)
                    buttonChoose.isEnabled = false
                }
            }
        }
    }
    private fun validationFields(): Boolean{
        return regionEditText.text.toString() != "" && addressEditText.text.toString() != "" && descriptionEditText.text.toString() != "" && spaceEditText.text.toString() != "" && priceEditText.text.toString() != "" && listUriImage.isNotEmpty()
    }
}