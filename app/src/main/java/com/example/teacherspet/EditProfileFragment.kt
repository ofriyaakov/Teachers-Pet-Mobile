package com.example.teacherspet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.teacherspet.databinding.FragmentEditProfileBinding
import com.example.teacherspet.databinding.FragmentLogInBinding
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.User
import com.example.teacherspet.model.dao.UserDao
import com.google.firebase.auth.FirebaseAuth

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null
    private var auth = FirebaseAuth.getInstance()
    private val userDetails: User? = null

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

        val returnButton: ImageButton = view.findViewById(R.id.returnButton)
        val saveButton: Button = view.findViewById(R.id.nextButton)

        var userDetailsDef = auth.currentUser?.uid?.let { Model.shared.getUser(it) }
        userDetailsDef?.addOnSuccessListener {
            userDetails?.password = userDetailsDef.result.data?.get("password").toString()

            binding?.nameInput?.setText(userDetailsDef.result.data?.get("name").toString())
            binding?.professionInput?.setText(userDetailsDef.result.data?.get("profession").toString())
            binding?.gradeInput?.setText(userDetailsDef.result.data?.get("grade").toString())
//          binding?.locationInput?.setText(userDetailsDef.result.data?.get("id").toString())
        }

        returnButton.setOnClickListener {
            onReturnButtonClick()
        }

        saveButton.setOnClickListener {
            saveChanges()
        }


    }

    private fun onReturnButtonClick(){
        //TODO: return to profile page
    }

    private fun saveChanges() {


        val userEdit = auth.currentUser?.let {
            User(
                id = it.uid,
                name = binding?.nameInput?.text?.toString() ?: "",
                profession = binding?.professionInput?.text?.toString() ?: "",
                grade = binding?.gradeInput?.text?.toString() ?: "",
                email = it.email ?: "",
                password = userDetails?.password ?: ""
            )
        }

        if (userEdit != null) {
            Model.shared.addUser(userEdit) {
                Model.Storage.CLOUDINARY
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}