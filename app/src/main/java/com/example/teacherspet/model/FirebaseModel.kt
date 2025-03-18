package com.example.teacherspet.model

import android.util.Log
import com.example.teacherspet.base.Constants
import com.example.teacherspet.base.EmptyCallback
import com.example.teacherspet.base.UsersCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

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

    fun getUser(id: String): Task<DocumentSnapshot> {
        return database.collection(Constants.Collections.USERS).document(id).get()
    }
}