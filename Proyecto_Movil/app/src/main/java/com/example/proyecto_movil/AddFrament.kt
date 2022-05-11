package com.example.proyecto_movil

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
import com.example.proyecto_movil.model.House
import com.example.proyecto_movil.model.Image
import com.example.proyecto_movil.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.*
import com.google.gson.Gson
import java.io.File

class AddFrament : Fragment() {

    //List =>
    private val listHouse: MutableList<House> = mutableListOf()
    private val listImage: MutableList<Image> = mutableListOf()

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

    //List to save operations in Firebase and internal storage android =>
    private val listUriImage: MutableList<Uri> = mutableListOf()
    private val listUrl: MutableList<String> = mutableListOf()


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frament_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       val id: Int = (activity as MainMenu).idUser

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
            url = "http://192.168.50.93:8080/users/$id"
            var house: House
            for (uriImage: Uri in listUriImage) {

                fileImage = File(uriImage.path)

                uploadTask =
                    storageReference.child("houses_images/${fileImage.name}").putFile(uriImage)


                uploadTask.addOnSuccessListener {
                    val firebaseUri: Task<Uri> = it.storage.downloadUrl

                    firebaseUri.addOnSuccessListener { uri ->
                        listUrl.add(uri.toString())
                        listImage.add(Image(null, uri.toString()))
                    }
                }
            }
            uploadTask =
                storageReference.child("houses_images/${fileImage.name}").putFile(listUriImage[2])

            uploadTask.addOnCompleteListener {
                Toast.makeText(context, "Termino la tarea ${listImage.size}", Toast.LENGTH_SHORT).show()

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
                val stringRequest = object: StringRequest(
                    Method.PUT, url,
                    {

                        Log.d("responseMessageHouse", it)
                        Toast.makeText(context, "Se ha añadido" ,Toast.LENGTH_SHORT).show()

                    }, {

                        Log.d("responseMessageHouse", it.toString())
                        Toast.makeText(context, "No se ha añadido" ,Toast.LENGTH_SHORT).show()

                    }) {

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }

                    override fun getBody(): ByteArray {
                        return stringJson.toByteArray()
                    }

                }

                mRequestQueue!!.add(stringRequest!!)


                for(imageUrl: String in listUrl){
                    Log.d("url", imageUrl)
                }

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

        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

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
}