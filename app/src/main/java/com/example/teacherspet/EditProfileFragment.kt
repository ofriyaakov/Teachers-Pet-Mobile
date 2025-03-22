package com.example.teacherspet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.teacherspet.databinding.FragmentEditProfileBinding
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.User
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null
    private var auth = FirebaseAuth.getInstance()
    private val userDetails: User? = null
    private var newImageUri: Uri? = null
    private var changedImage: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userDetailsDef = auth.currentUser?.uid?.let { Model.shared.getUser(it) }
        userDetailsDef?.addOnSuccessListener {
            userDetails?.password = userDetailsDef.result.data?.get("password").toString()

            binding?.nameInput?.setText(userDetailsDef.result.data?.get("name").toString())
            binding?.professionInput?.setText(userDetailsDef.result.data?.get("profession").toString())
            binding?.gradeInput?.setText(userDetailsDef.result.data?.get("grade").toString())

            if (userDetailsDef.result.data?.get("imageUri").toString() !== ""){
                Picasso.get()
                    .load(userDetailsDef.result.data?.get("imageUri").toString())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding?.postImage)

            }

        }

        binding?.returnButton?.setOnClickListener {
            onReturnButtonClick()
        }

        binding?.nextButton?.setOnClickListener {
            saveChanges()
        }

        binding?.postImage?.setOnClickListener {
            selectImage()
        }


    }

    private fun onReturnButtonClick(){
        backToMyPosts()
    }

    private fun saveChanges() {

        val userEdit = auth.currentUser?.let {
            User(
                id = it.uid,
                name = binding?.nameInput?.text?.toString() ?: "",
                profession = binding?.professionInput?.text?.toString() ?: "",
                grade = binding?.gradeInput?.text?.toString() ?: "",
                email = it.email ?: "",
                password = userDetails?.password ?: "",
                imageUri = newImageUri.toString(),
            )
        }

        if (userEdit != null) {
            Model.shared.addUser(userEdit) {
                Model.Storage.CLOUDINARY
            }
        }

        backToMyPosts()
    }

    private fun backToMyPosts() {
        findNavController().navigate(R.id.action_editProfileFragment_to_myPostsPageFragment)
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
        fun newInstance() =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}