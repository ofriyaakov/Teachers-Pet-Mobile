package com.example.teacherspet.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teacherspet.model.Post
import com.example.teacherspet.model.User

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAllPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg post: Post)

    @Query("SELECT * FROM post WHERE userId =:userId")
    fun getPostsByUserId(userId: String): LiveData<List<Post>>
}