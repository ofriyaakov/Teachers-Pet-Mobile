package com.example.teacherspet.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.teacherspet.model.Post.Companion.IMAGE_URI_KEY

@Entity
data class User(
    @PrimaryKey var id: String,
    var name: String,
    val grade: String,
    val profession: String,
    val email: String,
    var password: String,
    val imageUri: String
) {

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val GRADE_KEY = "grade"
        const val PROFESSION_KEY = "profession"
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val IMAGE_URI_KEY = "imageUri"
    }

    val json: Map<String, Any>
        get() = hashMapOf(
            ID_KEY to id,
            NAME_KEY to name,
            GRADE_KEY to grade,
            PROFESSION_KEY to PROFESSION_KEY,
            EMAIL_KEY to email,
            PASSWORD_KEY to password,
            IMAGE_URI_KEY to imageUri,
            )

}
