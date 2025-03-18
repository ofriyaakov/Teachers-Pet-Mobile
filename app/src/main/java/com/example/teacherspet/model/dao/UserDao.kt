package com.example.teacherspet.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teacherspet.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id =:id")
    fun getUsertById(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM User WHERE email =:email")
    fun getUserByEmail(email: String): User
}