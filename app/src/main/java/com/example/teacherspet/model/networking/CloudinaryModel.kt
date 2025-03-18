//package com.example.teacherspet.model.networking
//
//import android.content.Context
//import android.graphics.Bitmap
//import com.cloudinary.android.MediaManager
//import com.cloudinary.android.callback.ErrorInfo
//import com.cloudinary.android.callback.UploadCallback
//import com.cloudinary.android.policy.GlobalUploadPolicy
//import com.example.teacherspet.BuildConfig
//import com.example.teacherspet.base.TeachersPetApplication
////import com.example.teacherspet.utils.extensions.toFile
//import java.io.File
//import java.io.FileOutputStream
//
//class CloudinaryModel {
//    init {
//        val config = mapOf(
//            "cloud_name" to BuildConfig.CLOUD_NAME,
//            "api_key" to BuildConfig.API_KEY,
//            "api_secret" to BuildConfig.API_SECRET
//        )
//
//        TeachersPetApplication.Globals.context?.let {
//            MediaManager.init(it, config)
//            MediaManager.get().globalUploadPolicy = GlobalUploadPolicy.defaultPolicy()
//        }
//    }
//}