package com.example.teacherspet.model

import com.example.teacherspet.base.EmptyCallback

class Model private constructor() {

    enum class Storage {
        FIREBASE,
        CLOUDINARY
    }

    private val firebaseModel = FirebaseModel()

    companion object {
        val shared = Model()
    }

    fun add(user: User, callback: EmptyCallback) {
        firebaseModel.add(user) {
            firebaseModel.add(user, callback)
        }
    }
}