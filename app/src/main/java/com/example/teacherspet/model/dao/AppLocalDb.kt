package com.example.teacherspet.model.dao

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teacherspet.base.TeachersPetApplication
import com.example.teacherspet.model.User
import com.example.teacherspet.model.Post

@Database(entities = [User::class, Post::class], version = 5)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}

@Dao
object AppLocalDb {

    val database: AppLocalDbRepository by lazy {

        val context = TeachersPetApplication.Globals.context ?: throw IllegalStateException("Application context is missing")

        Room.databaseBuilder(
            context = context,
            klass = AppLocalDbRepository::class.java,
            name = "dbFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}