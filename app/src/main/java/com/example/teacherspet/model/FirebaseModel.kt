package com.example.teacherspet.model

import android.util.Log
import com.example.teacherspet.base.Constants
import com.example.teacherspet.base.EmptyCallback
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
        database.collection(Constants.Collections.USERS).document(user.id).set(user)
            .addOnCompleteListener {
                callback()
            }
            .addOnFailureListener {
                Log.d("TAG", it.toString() + it.message)
            }
    }
}