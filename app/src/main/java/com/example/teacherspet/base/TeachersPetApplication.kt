package com.example.teacherspet.base

import android.app.Application
import android.content.Context

class TeachersPetApplication: Application() {

    object Globals {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        Globals.context = applicationContext
    }
}