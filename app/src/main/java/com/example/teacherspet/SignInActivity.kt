package com.example.teacherspet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teacherspet.model.Model
import com.example.teacherspet.model.User

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.nextButton)

        saveButton.setOnClickListener {
            onSaveClicked()
        }
    }

    private fun onSaveClicked() {
        val idInput: EditText = findViewById(R.id.idInput)
        val nameInput: EditText = findViewById(R.id.nameInput)
        val professionInput: EditText = findViewById(R.id.professionInput)
        val gradeInput: EditText = findViewById(R.id.gradeInput)
        val emailInput: EditText = findViewById(R.id.emailInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)

        val user = User(
            id = idInput.text?.toString() ?: "",
            name = nameInput.text?.toString() ?: "",
            profession = professionInput.text?.toString() ?: "",
            grade = gradeInput.text?.toString() ?: "",
            email = emailInput.text?.toString() ?: "",
            password = passwordInput.text?.toString() ?: ""
        )

        Model.shared.add(user) {
            Model.Storage.CLOUDINARY
        }

    }
}

fun onReturnButtonClick() {
//    startActivity(Intent(this, LandingPageActivity::class.java))
}