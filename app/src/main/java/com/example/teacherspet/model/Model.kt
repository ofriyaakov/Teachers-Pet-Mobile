package com.example.teacherspet.model

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

    companion object {
        val shared = Model()
    }

    fun addUser(user: User, callback: EmptyCallback) {
        firebaseModel.addUser(user) {
            firebaseModel.addUser(user, callback)
        }
    }

    fun addPost(post: Post, callback: EmptyCallback) {
        firebaseModel.addPost(post) {
            firebaseModel.addPost(post, callback)
        }
    }

}