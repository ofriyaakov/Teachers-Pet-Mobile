package com.example.teacherspet.base

import com.example.teacherspet.model.User

typealias EmptyCallback = () -> Unit
typealias UsersCallback = (List<User>) -> Unit

object Constants {

    object Collections {
        const val USERS = "users"
        const val POSTS = "posts"
    }
}