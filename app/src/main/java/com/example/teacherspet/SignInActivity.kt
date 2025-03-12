package com.example.teacherspet

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
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

        val gradeDropdown: Spinner = findViewById(R.id.gradeDropdown)

        ArrayAdapter.createFromResource(
            this,
            R.array.grade_dropdown_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            gradeDropdown.adapter = adapter
        }

        val professionDropdown: Spinner = findViewById(R.id.professionDropdown)

        ArrayAdapter.createFromResource(
            this,
            R.array.profession_dropdown_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            professionDropdown.adapter = adapter
        }
    }

    private fun onSaveClicked() {
        val user = User(
            id = findViewById(R.id.idInput) ?: "",
            name = findViewById(R.id.nameInput) ?: "",
            profession = findViewById(R.id.professionDropdown) ?: "",
            grade = findViewById(R.id.gradeDropdown) ?: "",
            email = findViewById(R.id.emailInput) ?: "",
            password = findViewById(R.id.passwordInput) ?: ""
        )

        Model.shared.add(user) {
            Model.Storage.CLOUDINARY
        }

    }
}

fun onReturnButtonClick() {
//    startActivity(Intent(this, LandingPageActivity::class.java))
}