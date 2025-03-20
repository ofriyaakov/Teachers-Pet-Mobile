package com.example.teacherspet.model

import android.graphics.Bitmap
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
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
    private val cloudinaryModel = CloudinaryModel()

    private val database: AppLocalDbRepository = AppLocalDb.database
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    val posts: LiveData<List<Post>> = database.postDao().getAllPosts()
    val loadingState: MutableLiveData<LoadingState> = MutableLiveData<LoadingState>()

    companion object {
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

    fun getUser(id: String): Task<DocumentSnapshot> {
        return firebaseModel.getUser(id)
    }

    fun addPost(post: Post, image: Bitmap?, storage: Storage, callback: EmptyCallback) {
        firebaseModel.addPost(post) {
            image?.let {
                uploadTo(
                    storage,
                    image = image,
                    name = post.id,
                    callback = { uri ->
                        if (!uri.isNullOrBlank()) {
                            val newPost = post.copy(imageUri = uri)
                            firebaseModel.addPost(newPost, callback)
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

    fun refreshAllPosts() {
        Log.d("ALL POSTS - 06", database.postDao().getAllPosts().value.toString())
        // TODO: use the comments in case we want to order by last update time
        loadingState.postValue(LoadingState.LOADING)
//        val lastUpdated: Long = Post.lastUpdated
            firebaseModel.getAllPosts() { posts ->
                Log.d("ALL POSTS - 02", posts.toString())
            executor.execute {
//                var currentTime = lastUpdated
                for (post in posts) {
                    Log.d("ALL POSTS - 03 post", post.toString())
                    database.postDao().insertAll(post)
//                    student.lastUpdated?.let {
//                        if (currentTime < it) {
//                            currentTime = it
//                        }
//                    }
                }

//                Student.lastUpdated = currentTime
                loadingState.postValue(LoadingState.LOADED)
            }
        }
    }

}
