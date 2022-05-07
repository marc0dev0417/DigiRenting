package com.example.proyecto_movil

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment

import com.google.firebase.storage.*
import java.io.File

class AddFrament : Fragment() {

    private lateinit var buttonChoose: Button

    //ImageViews =>
    private lateinit var imageViewFirst: ImageView
    private lateinit var imageViewSecond: ImageView
    private lateinit var imageViewThird: ImageView

    //Counts to controller the state in Button Choose =>
    private var count = 0

    //Utils to firebase =>
    private lateinit var storageReference: StorageReference
    private lateinit var uploadTask: UploadTask
    private lateinit var fileImage: File
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

        //Images view put reference id =>
        imageViewFirst = view.findViewById(R.id.image_view_first)
        imageViewSecond = view.findViewById(R.id.image_view_second)
        imageViewThird = view.findViewById(R.id.image_view_third)

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
                    buttonChoose.isEnabled = false
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
                    fileImage = File(imageUri.path)

                    uploadTask = storageReference.child("houses_images/${fileImage.name}").putFile(imageUri)

                    uploadTask.addOnSuccessListener {
                        Toast.makeText(context, "The image has been uploaded", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener{
                        Toast.makeText(context, "Not has been uploaded", Toast.LENGTH_LONG).show()
                    }
                }
                2 -> {
                    imageViewSecond.setImageURI(imageUri)
                }
                3 -> {
                    imageViewThird.setImageURI(imageUri)
                }
            }
            //listUri.add(imageUri)
        }
    }
}