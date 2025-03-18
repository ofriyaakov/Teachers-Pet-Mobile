package com.example.teacherspet.model

import android.R.attr.path
import android.R.string
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.teacherspet.base.Constants
import com.example.teacherspet.base.EmptyCallback
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File


class FirebaseModel {

    private val database = Firebase.firestore
    private val storage = Firebase.storage

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
        database.firestoreSettings = settings
    }

    fun addUser(user: User, callback: EmptyCallback) {
        database.collection(Constants.Collections.USERS).document(user.id).set(user.json)
            .addOnCompleteListener {
                callback()
            }
            .addOnFailureListener {
                Log.d("TAG", it.toString() + it.message)
            }
    }

    fun addPost(post: Post, callback: EmptyCallback) {

        val storageRef: StorageReference = storage.reference

//        path = Android.OS.Environment.GetExternalStoragePublicDirectory(
//            Android.OS.Environment.DirectoryPictures
//        ).AbsolutePath
//
//        val myPath: string = Path.Combine(path, "file.name")

//        Log.d("file - uri", uri.toString())

        val file = Uri.fromFile(File("storage/emulated/0/Pictures/Screenshots/Screenshot_20250316-213742.png")) // Replace with your file path and name
//        val inputStream = contentResolver.openInputStream(uri)
        val riversRef = storageRef.child("gs://teacher-s-pet-6c42a.firebasestorage.app/images") // Specify the storage path

        val uploadTask = riversRef.putFile(file)

// Create a reference to "file"
        val mountainsRef = storageRef.child("file.jpg")
//        var post = post

        if (file !== null) {

//            imageRef.putFile(uri)
//                .addOnSuccessListener { taskSnapshot ->
//                    imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
//                        post = Post(id = post.id, userId=post.userId, imageUri = downloadUrl.toString(), description=post.description)}}
//
            Log.d("file file", file.toString())
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content type, etc.
                // Get a URL to the uploaded content
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    //Do something with the uri
                }
            }


            database.collection(Constants.Collections.POSTS).document(post.id).set(post.json)
                .addOnCompleteListener {
                    callback()
                }
                .addOnFailureListener {
                    Log.d("TAG", it.toString() + it.message)
                }
        }
    }

    fun uploadImage(image: Bitmap, name: String, callback: (String?) -> Unit) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/$name.jpg")
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            callback(null)
        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }
        }
    }
}