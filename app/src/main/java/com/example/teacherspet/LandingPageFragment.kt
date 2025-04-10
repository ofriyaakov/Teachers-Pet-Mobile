package com.example.teacherspet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

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
        findNavController().navigate(R.id.action_landingPageFragment_to_logInFragment)
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