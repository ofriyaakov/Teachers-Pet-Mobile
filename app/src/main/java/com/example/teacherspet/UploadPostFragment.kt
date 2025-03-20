package com.example.teacherspet
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
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
import android.util.Log
import androidx.navigation.Navigation
import com.example.teacherspet.databinding.FragmentUploadPostBinding
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.Post
import com.example.teacherspet.model.User
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class UploadPostFragment : Fragment() {
    private var binding: FragmentUploadPostBinding? = null
    private lateinit var postImage: ImageView
    private lateinit var postDescription: EditText
    private lateinit var uploadImageButton: Button
    private lateinit var postButton: Button
    private lateinit var cancelButton: Button
    private var selectedImageUri: Uri? = null
    private val userDetails: User? = User(
        id = "",
        name = "",
        grade = "",
        profession = "",
        email = "",
        password = "")

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadPostBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postImage = view.findViewById(R.id.postImage)
        postDescription = view.findViewById(R.id.description)
        uploadImageButton = view.findViewById(R.id.uploadImageButton)

        var userDetailsDef = auth.currentUser?.uid?.let { Model.shared.getUser(it) }

        userDetailsDef?.addOnSuccessListener {
               userDetails?.id = userDetailsDef.result.data?.get("id").toString()
               userDetails?.name = userDetailsDef.result.data?.get("name").toString()
        }

        postButton = view.findViewById(R.id.postButton)
        cancelButton = view.findViewById(R.id.cancelButton)
        uploadImageButton.setOnClickListener {
            selectImage()
        }
        postButton.setOnClickListener {
            onPostClicked()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
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

    private fun onPostClicked() {

        val post = Post(
            id = UUID.randomUUID().toString(),
            userId = userDetails?.id ?: "",
            imageUri = selectedImageUri?.toString() ?: "",
            description = postDescription?.text?.toString() ?: "",
            userName = userDetails?.name ?: ""
        )

        binding?.postImage?.isDrawingCacheEnabled = true
        binding?.postImage?.buildDrawingCache()
        val bitmap = (binding?.postImage?.drawable as BitmapDrawable).bitmap

        Model.shared.addPost(post, bitmap, Model.Storage.CLOUDINARY) {
            view?.let { Navigation.findNavController(it).popBackStack() }
        }
    }
    private fun clearForm() {
        postDescription?.text?.clear()
        postImage?.setImageResource(R.drawable.ic_launcher_foreground)
        postImage?.visibility = View.GONE
    }
    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UploadPostFragment().apply {
            }
    }
}