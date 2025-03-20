package com.example.teacherspet.base

import com.example.teacherspet.model.Post
import com.example.teacherspet.model.User

typealias EmptyCallback = () -> Unit
typealias UsersCallback = (List<User>) -> Unit
typealias PostsCallback = (List<Post>) -> Unit

object Constants {

    object Collections {
        const val USERS = "users"
        const val POSTS = "posts"
    }
}