package com.example.teacherspet.model

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teacherspet.base.EmptyCallback
import java.util.concurrent.Executors
import com.example.teacherspet.model.dao.AppLocalDb
import com.example.teacherspet.model.dao.AppLocalDbRepository

class Model private constructor() {

    enum class Storage {
        FIREBASE,
        CLOUDINARY
    }

    enum class LoadingState {
        LOADING,
        LOADED
    }

    private val firebaseModel = FirebaseModel()
    private val cloudinaryModel = CloudinaryModel()
    companion object {
//        val shared = Model()
        val shared = try {
            Model()
        } catch (e: Exception) {
        Log.d("error - debug", e.message.toString())
        Model()
        }
    }

    fun addUser(user: User, callback: EmptyCallback) {
        firebaseModel.addUser(user) {
            firebaseModel.addUser(user, callback)
        }
    }

//    fun addPost(post: Post, uri: Uri?, callback: EmptyCallback) {
//        firebaseModel.addPost(post, uri) {
//            firebaseModel.addPost(post, uri, callback)
//        }
//    }

    fun addPost(post: Post, image: Bitmap?, storage: Storage, callback: EmptyCallback) {
        firebaseModel.addPost(post) {
            image?.let {
                uploadTo(
                    storage,
                    image = image,
                    name = post.id,
                    callback = { uri ->
                        if (!uri.isNullOrBlank()) {
                            val st = post.copy(imageUri = uri)
                            firebaseModel.addPost(st, callback)
                        } else {
                            callback()
                        }
                    },
                )
            } ?: callback()
        }
    }

    private fun uploadTo(storage: Storage, image: Bitmap, name: String, callback: (String?) -> Unit) {
        when (storage) {
            Storage.FIREBASE -> {
                uploadImageToFirebase(image, name, callback)
            }
            Storage.CLOUDINARY -> {
                uploadImageToCloudinary(
                    bitmap = image,
                    name = name,
                    onSuccess = callback,
                    onError = { callback(null) }
                )
            }
        }
    }

    private fun uploadImageToFirebase(
        image: Bitmap,
        name: String,
        callback: (String?) -> Unit
    ) {
        firebaseModel.uploadImage(image, name, callback)
    }

    private fun uploadImageToCloudinary(
        bitmap: Bitmap,
        name: String,
        onSuccess: (String?) -> Unit,
        onError: (String?) -> Unit
    ) {
        cloudinaryModel.uploadImage(
            bitmap = bitmap,
            name = name,
            onSuccess = onSuccess,
            onError = onError
        )
    }

}