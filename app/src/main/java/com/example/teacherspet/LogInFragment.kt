package com.example.teacherspet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.teacherspet.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    private var binding: FragmentLogInBinding? = null
    val auth = FirebaseAuth.getInstance()

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
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val returnButton: ImageButton = view.findViewById(R.id.returnButton)
        val nextButton: Button = view.findViewById(R.id.nextButton)

        returnButton.setOnClickListener {
            onReturnButtonClick()
        }

        nextButton.setOnClickListener {
            onNextClicked()
        }

    }

    private fun onReturnButtonClick() {
        findNavController().navigate(R.id.action_logInFragment_to_landingPageFragment)
    }

    private fun onNextClicked() {
        auth.signInWithEmailAndPassword(binding?.emailInput?.text.toString(),
            binding?.passwordInput?.text.toString()).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                Log.d("LogIn", "LogIn was successful")
                //TODO: change nav when other pages are ready
                findNavController().navigate(R.id.action_logInFragment_to_aiHelperFragment2)

                val mainActivity = activity as MainActivity
                mainActivity.showBottomNavBar()
            }
            else {
                Log.d("LogIn", task.exception.toString())
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LogInFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}