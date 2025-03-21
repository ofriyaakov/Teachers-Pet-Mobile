package com.example.teacherspet

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.example.teacherspet.base.Constants
import com.example.teacherspet.databinding.FragmentEditPostBinding
import com.example.teacherspet.databinding.FragmentEditProfileBinding
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import java.util.UUID

private const val POST_ID = "postId"

class EditPostFragment : Fragment() {
    private var postId: String? = null
    private var binding: FragmentEditPostBinding? = null
    private var postDetails: Post? = null
    private var newImageUri: Uri? = null
    private var changedImage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getString(POST_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPostBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text: EditText = view.findViewById(R.id.description)
        text.setText(postId)

        val postButton: Button = view.findViewById(R.id.postButton)
        val cancelButton: Button = view.findViewById(R.id.cancelButton)
        val uploadImageButton: Button = view.findViewById(R.id.uploadImageButton)

        uploadImageButton.setOnClickListener {
            selectImage()
        }
        postButton.setOnClickListener {
            onPostClicked()
        }
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_editPostFragment_to_myPostsPageFragment)
        }

        val post = postId?.let { Model.shared.getPost(it) }
        post?.addOnSuccessListener {
            binding?.description?.setText(post.result.data?.get("description").toString())


            postDetails?.id = postId.toString()
            postDetails?.userId = post.result.data?.get("userId").toString()
            postDetails?.imageUri = post.result.data?.get("imageUri").toString()
            postDetails?.description = post.result.data?.get("description").toString()
            postDetails?.userName = post.result.data?.get("userName").toString()

            Picasso.get()
                .load(post.result.data?.get("imageUri").toString())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding?.postImage)
        }
    }

    private fun onPostClicked() {
        Log.d("SAVE", "IM HERE")
        val db = FirebaseFirestore.getInstance()
        val documentRef = postId?.let { db.collection(Constants.Collections.POSTS).document(it) }
        Log.d("SAVE", changedImage.toString())

        if (changedImage) {
            documentRef?.update("description", binding?.description?.text.toString())
            documentRef?.update("imageUri", newImageUri?.toString())

        } else {
            documentRef?.update("description", binding?.description?.text.toString())
        }

        changedImage = false
        findNavController().navigate(R.id.action_editPostFragment_to_myPostsPageFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            newImageUri = data?.data
            binding?.postImage?.setImageURI(newImageUri)
            binding?.postImage?.visibility = View.VISIBLE
            changedImage = true
        }

    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
        @JvmStatic
        fun newInstance(postId: String) =
            EditPostFragment().apply {
                arguments = Bundle().apply {
                    putString(POST_ID, postId)
                }
            }
    }
}