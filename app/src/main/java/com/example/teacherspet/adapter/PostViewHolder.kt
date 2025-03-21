package com.example.teacherspet.adapter

import android.util.Log
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.teacherspet.R
import com.example.teacherspet.databinding.SinglePostBinding
import com.example.teacherspet.model.Post
import com.squareup.picasso.Picasso
import com.example.teacherspet.OnItemClickListener

class PostViewHolder(
    private val binding: SinglePostBinding,
    listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(binding.root) {

        private var post: Post? = null

        init {
            itemView.setOnClickListener {
                Log.d("TAG", "On click listener on position $adapterPosition yayyyy")
//                    listener?.onItemClick(adapterPosition)
                listener?.onItemClick(post)
            }
        }

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