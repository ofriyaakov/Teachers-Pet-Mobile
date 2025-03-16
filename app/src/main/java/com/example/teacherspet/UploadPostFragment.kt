package com.example.teacherspet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.net.Uri
import android.provider.MediaStore
import androidx.navigation.fragment.findNavController

class UploadPostFragment : Fragment() {

    private lateinit var postImage: ImageView
    private lateinit var postDescription: EditText
    private lateinit var uploadImageButton: Button
    private lateinit var postButton: Button
    private lateinit var cancelButton: Button
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_upload_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postImage = view.findViewById(R.id.postImage)
        postDescription = view.findViewById(R.id.description)
        uploadImageButton = view.findViewById(R.id.uploadImageButton)
        postButton = view.findViewById(R.id.postButton)
        cancelButton = view.findViewById(R.id.cancelButton)

        uploadImageButton.setOnClickListener {
            selectImage()
        }

        postButton.setOnClickListener {
//            uploadPost()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            postImage.setImageURI(selectedImageUri)
            postImage.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadPostFragment().apply {
            }
    }
}