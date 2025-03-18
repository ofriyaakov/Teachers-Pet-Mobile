package com.example.teacherspet.model

import android.util.Log
import com.example.teacherspet.base.Constants
import com.example.teacherspet.base.EmptyCallback
import com.example.teacherspet.base.UsersCallback
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class FirebaseModel {

    private val database = Firebase.firestore

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
        database.firestoreSettings = settings
    }

    fun add(user: User, callback: EmptyCallback) {
        Log.d("onSavedClicked - 04", user.json.toString())
        database.collection(Constants.Collections.USERS).document(user.id).set(user.json)
            .addOnCompleteListener {
                Log.d("onSavedClicked - addOnCompleteListener", user.json.toString())
                callback()
            }
            .addOnFailureListener {
                Log.d("TAG", it.toString() + it.message)
                Log.d("onSavedClicked - 06", it.toString() + it.message)
            }
        Log.d("onSavedClicked - 05", "${ database.collection(Constants.Collections.USERS).get() }")
    }
}