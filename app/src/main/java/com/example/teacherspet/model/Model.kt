package com.example.teacherspet.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teacherspet.base.EmptyCallback
import java.util.concurrent.Executors
import com.example.teacherspet.model.dao.AppLocalDb
import com.example.teacherspet.model.dao.AppLocalDb.database
import com.example.teacherspet.model.dao.AppLocalDbRepository
import com.example.teacherspet.model.dao.UserDao
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot

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
//    private val database: AppLocalDbRepository = AppLocalDb.database
//    val loadingState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()
//    private var executor = Executors.newSingleThreadExecutor()
//    val users: LiveData<List<User>> = database.userDao().getAllUsers()



    companion object {
        val shared = Model()
    }

    fun add(user: User, callback: EmptyCallback) {
        Log.d("onSavedClicked-03", user.toString())
        firebaseModel.add(user) {
            firebaseModel.add(user, callback)
        }
    }

    fun getUser(id: String): Task<DocumentSnapshot> {
        return firebaseModel.getUser(id)
    }

//    fun printAllUsers() {
//        loadingState.postValue(LoadingState.LOADING)
//        val lastUpdated: Long = User.lastUpdated
//        firebaseModel.getAllUsers() { users ->
////            Log.d("USERS-01", users.toString())
//            executor.execute {
//                var currentTime = lastUpdated
//                for (user in users) {
////                    database.userDao().insertAll(users)
////                    user.lastUpdated?.let {
////                        if (currentTime < it) {
////                            currentTime = it
////                        }
////                    }
//                    Log.d("USERS-02", user.toString())
//                }
//
//                User.lastUpdated = currentTime
//                loadingState.postValue(LoadingState.LOADED)
//            }
//        }
//    }
}