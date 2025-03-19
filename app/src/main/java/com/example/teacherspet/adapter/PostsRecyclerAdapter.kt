package com.example.teacherspet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
//import com.example.teacherspet.OnItemClickListener
import com.example.teacherspet.R
import com.example.teacherspet.databinding.SinglePostBinding
import com.example.teacherspet.model.Post

class PostsRecyclerAdapter(private var posts: List<Post>?): RecyclerView.Adapter<PostViewHolder>() {

//        var listener: OnItemClickListener? = null

        fun update(posts: List<Post>?) {
            this.posts = posts
        }

        override fun getItemCount(): Int = posts?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val inflator = LayoutInflater.from(parent.context)
            val binding = SinglePostBinding.inflate(inflator, parent, false)
            return PostViewHolder(binding)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(
                post = posts?.get(position),
                position = position
            )
        }
    }