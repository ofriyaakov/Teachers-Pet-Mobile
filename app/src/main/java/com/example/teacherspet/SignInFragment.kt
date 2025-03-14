package com.example.teacherspet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.teacherspet.model.Model
import com.example.teacherspet.databinding.FragmentSignInBinding
import com.example.teacherspet.model.User

class SignInFragment : Fragment() {

    private var binding: FragmentSignInBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    fun onReturnButtonClick() {
        findNavController().navigate(R.id.action_signInFragment_to_landingPageFragment)
    }

    private fun onSaveClicked() {

        val user = User(
            id = binding?.idInput?.text?.toString() ?: "",
            name = binding?.nameInput?.text?.toString() ?: "",
            profession = binding?.professionInput?.text?.toString() ?: "",
            grade = binding?.gardeInput?.text?.toString() ?: "",
            email = binding?.emailInput?.text?.toString() ?: "",
            password = binding?.passwordInput?.text?.toString() ?: ""
        )

        Model.shared.add(user) {
            Model.Storage.CLOUDINARY
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
            }
    }
}