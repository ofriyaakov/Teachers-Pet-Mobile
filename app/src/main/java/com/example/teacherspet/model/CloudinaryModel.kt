package com.example.teacherspet.model

import android.graphics.Bitmap
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.example.teacherspet.BuildConfig
import com.example.teacherspet.base.TeachersPetApplication
import com.example.teacherspet.utils.extensions.toFile
import java.io.File

class CloudinaryModel {

    companion object {
        private var isInitialized = false
    }


    init {
        if (!isInitialized) {
            val config = mapOf(
                "cloud_name" to BuildConfig.CLOUD_NAME,
                "api_key" to BuildConfig.API_KEY,
                "api_secret" to BuildConfig.API_SECRET
            )


            TeachersPetApplication.Globals.context?.let {
                MediaManager.init(it, config)
                MediaManager.get().globalUploadPolicy = GlobalUploadPolicy.defaultPolicy()
            }

            isInitialized = true
        }
    }

    fun uploadImage(
        bitmap: Bitmap,
        name: String,
        onSuccess: (String?) -> Unit,
        onError: (String?) -> Unit
    ) {
        val context = TeachersPetApplication.Globals.context ?: return
        val file: File = bitmap.toFile(context, name)

        MediaManager.get().upload(file.path)
            .option("folder", "images")
            .callback(object  : UploadCallback {
                override fun onStart(requestId: String?) {

                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {

                }

                override fun onSuccess(requestId: String?, resultData: Map<*, *>) {
                    val url = resultData["secure_url"] as? String ?: ""
                    onSuccess(url)
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                    onError(error?.description ?: "Unknown error")
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {

                }

            })
            .dispatch()
    }
}