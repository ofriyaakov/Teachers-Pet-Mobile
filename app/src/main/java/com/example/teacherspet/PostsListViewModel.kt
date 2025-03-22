package com.example.teacherspet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post

class PostsListViewModel(): ViewModel() {

    var posts: LiveData<List<Post>> = Model.shared.posts
    var postsByUserId: LiveData<List<Post>> = Model.shared.postsByUserId

    fun refreshAllPosts() {
        Model.shared.refreshAllPosts()
    }

    fun refreshPostsByUserId() {
        Model.shared.refreshPostsByUserId()
    }

}