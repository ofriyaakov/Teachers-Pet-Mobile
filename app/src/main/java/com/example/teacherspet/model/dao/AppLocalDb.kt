package com.example.teacherspet.model.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.teacherspet.model.User
import com.example.teacherspet.base.TeachersPetApplication

@Database(entities = [User::class], version = 3)
abstract class AppLocalDbRepository: RoomDatabase() {
    abstract fun userDao(): UserDao
}

object AppLocalDb {
    val database: AppLocalDbRepository by lazy {
        val context = TeachersPetApplication.Globals.context ?: throw IllegalStateException("Application context is missing")

        Room.databaseBuilder(
            context = context,
            klass = AppLocalDbRepository::class.java,
            name = "dbFileName.db"
        ).fallbackToDestructiveMigration().build()
    }
}