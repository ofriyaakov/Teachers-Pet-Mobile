package com.example.teacherspet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post

class PostsListViewModel(): ViewModel() {

    var posts: LiveData<List<Post>> = Model.shared.posts

    fun refreshAllPosts() {
        Model.shared.refreshAllPosts()
    }

    fun refreshPostsByUserId(userId: String) {
        Model.shared.refreshPostsByUserId(userId)
    }

//    fun filterPosts(userId: String) {
//        listOf(posts).filter { it.userId == userId}
//    }
}