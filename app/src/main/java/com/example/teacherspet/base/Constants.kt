package com.example.teacherspet.base

import com.example.teacherspet.model.Post

typealias EmptyCallback = () -> Unit
typealias PostsCallback = (List<Post>) -> Unit

object Constants {

    object Collections {
        const val USERS = "users"
        const val POSTS = "posts"
    }
}