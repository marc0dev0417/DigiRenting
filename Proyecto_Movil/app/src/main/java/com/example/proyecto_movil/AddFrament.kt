package com.example.proyecto_movil

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.*
import java.io.File
import java.lang.Exception
import java.net.URL
import java.util.concurrent.Executors

class AddFrament : Fragment() {

    //Buttons =>
    private lateinit var buttonChoose: Button
    private lateinit var buttonUpload: Button

    //ImageViews =>
    private lateinit var imageViewFirst: ImageView
    private lateinit var imageViewSecond: ImageView
    private lateinit var imageViewThird: ImageView
    //fields =>
    private lateinit var regionEditText: EditText

    //Counts to controller the state in Button Choose =>
    private var count = 0

    //Utils to firebase =>
    private lateinit var storageReference: StorageReference
    private lateinit var uploadTask: UploadTask
    private lateinit var fileImage: File

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

        storageReference = FirebaseStorage.getInstance().reference

        buttonChoose = view.findViewById(R.id.button_choose)
        buttonUpload = view.findViewById(R.id.button_upload)

        regionEditText = view.findViewById(R.id.region_add_house)

        //Images view put reference id =>
        imageViewFirst = view.findViewById(R.id.image_view_first)
        imageViewSecond = view.findViewById(R.id.image_view_second)
        imageViewThird = view.findViewById(R.id.image_view_third)


        buttonUpload.setOnClickListener {

            for (uriImage: Uri in listUriImage) {

                fileImage = File(uriImage.path)

                uploadTask =
                    storageReference.child("houses_images/${fileImage.name}").putFile(uriImage)


                uploadTask.addOnSuccessListener {
                    val firebaseUri: Task<Uri> = it.storage.downloadUrl

                    firebaseUri.addOnSuccessListener { uri ->
                        listUrl.add(uri.toString())
                    }

                }
            }
            uploadTask =
                storageReference.child("houses_images/${fileImage.name}").putFile(listUriImage[2])

            uploadTask.addOnCompleteListener {
                Toast.makeText(context, "Termino la tarea ${listUrl.size}", Toast.LENGTH_SHORT).show()

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
    fun selectedImage() {

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
            //listUri.add(imageUri)
        }
    }

}