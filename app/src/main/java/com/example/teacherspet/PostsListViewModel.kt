package com.example.teacherspet

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post

class PostsListViewModel: ViewModel() {

    var posts: LiveData<List<Post>> = Model.shared.posts

    fun refreshAllPosts() {
        Model.shared.refreshAllPosts()
    }
}