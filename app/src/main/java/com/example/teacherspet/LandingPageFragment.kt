package com.example.teacherspet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Properties

class LandingPageFragment : Fragment() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_landing_page, container, false)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInButton: Button = view.findViewById(R.id.signInButton)
        val logInButton: Button = view.findViewById(R.id.logInButton)

        signInButton.setOnClickListener {
            toSignIn()
        }

        logInButton.setOnClickListener {
            toLogIn()
        }

    }

    private fun toSignIn(){
        findNavController().navigate(R.id.action_landingPageFragment_to_signInFragment)
    }

    private fun toLogIn(){
//        startActivity(Intent(this, LogInActivity::class.java))
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            LandingPageFragment().apply {
//                arguments = Bundle().apply {
//                }
            }
    }
}