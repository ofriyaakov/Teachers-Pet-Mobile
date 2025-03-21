package com.example.teacherspet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.teacherspet.R
import com.example.teacherspet.model.Post

class PostsAdapter(private val posts: List<Post>?): BaseAdapter() {

        override fun getCount(): Int = posts?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getItemId(position: Int): Long  = 0

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val inflator = LayoutInflater.from(parent?.context)
            val view = convertView ?: inflator.inflate(R.layout.single_post, parent, false)

            val post = posts?.get(position)

            val userName: TextView? = view?.findViewById(R.id.userNameTitle)
            val description: TextView? = view?.findViewById(R.id.postDescriptionTitle)
            val postImage: ImageView? = view?.findViewById(R.id.postImage2)

            userName?.text = post?.userName
            description?.text = post?.description
//            postImage = toFile(post?.imageUri)

            return view!!
        }
    }