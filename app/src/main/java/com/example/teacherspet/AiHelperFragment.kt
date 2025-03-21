package com.example.teacherspet

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AiHelperFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sendButton: ImageButton = view.findViewById(R.id.sendButton)
        sendButton.setOnClickListener(){
            sendToAI(view)
        }

        val textView: TextView = view.findViewById(R.id.textView)
        textView.movementMethod = ScrollingMovementMethod()

    }

    fun sendToAI(view: View) {
        val prompt: EditText = view.findViewById(R.id.editTextTextMultiLine)
        val answerView: TextView = view.findViewById(R.id.textView)


        val apiKey = BuildConfig.GEMINI_API_KEY
        val generativeModel =
            GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = apiKey)

        lifecycleScope.launch {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt.text.toString())
            }
            response.text?.let { Log.d("KFIRIN", it) }
            answerView.text = response.text.toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_helper, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AiHelperFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}