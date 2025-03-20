package com.example.teacherspet.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.teacherspet.R
import com.example.teacherspet.databinding.SinglePostBinding
import com.example.teacherspet.model.Post
import com.squareup.picasso.Picasso

class PostViewHolder(
    private val binding: SinglePostBinding,
    ): RecyclerView.ViewHolder(binding.root) {

        private var post: Post? = null

        fun bind(post: Post?) {
            this.post = post
            binding.userNameTitle.text = post?.userName
            binding.postDescriptionTitle.text = post?.description

            post?.imageUri?.let {
                if (it.isNotBlank()) {
                    Picasso.get()
                        .load(it)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.postImage2)
                }

            }
        }
    }