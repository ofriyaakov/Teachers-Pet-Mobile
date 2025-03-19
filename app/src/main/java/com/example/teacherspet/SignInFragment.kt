package com.example.teacherspet
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.teacherspet.model.Model
import com.example.teacherspet.databinding.FragmentSignInBinding
import com.example.teacherspet.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInFragment : Fragment() {
    private var binding: FragmentSignInBinding? = null
    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val returnButton: ImageView = view.findViewById(R.id.returnButton)
        val saveButton: Button = view.findViewById(R.id.nextButton)

        returnButton.setOnClickListener {
            onReturnButtonClick()
        }

        saveButton.setOnClickListener {
            onSaveClicked()
        }

    }

    private fun onReturnButtonClick() {
//        Model.shared.printAllUsers()
        findNavController().navigate(R.id.action_signInFragment_to_landingPageFragment)
    }

    private fun onSaveClicked() {

        val user = User(
            id = "",
            name = binding?.nameInput?.text?.toString() ?: "",
            profession = binding?.professionInput?.text?.toString() ?: "",
            grade = binding?.gradeInput?.text?.toString() ?: "",
            email = binding?.emailInput?.text?.toString() ?: "",
            password = binding?.passwordInput?.text?.toString() ?: ""
        )



        Log.d("AUTH", "Starting")
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("SignIn", "SignIn was successful")
                    user.id = task.result.user?.uid.toString()

                    Model.shared.addUser(user) {
                        Model.Storage.CLOUDINARY
                    }

                }
                else {
                    Log.d("SignIn", task.exception.toString())
                }
            }

        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LogInAfterSignIn", "LogIn was successful")
                }
                else {
                    Log.d("LogInAfterSignIn", task.exception.toString())
                }
            }


    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
            }
    }
}