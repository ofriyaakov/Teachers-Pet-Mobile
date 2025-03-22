package com.example.teacherspet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: String,
    var name: String,
    val grade: String,
    val profession: String,
    val email: String,
    var password: String,
) {

    companion object {
        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val GRADE_KEY = "grade"
        const val PROFESSION_KEY = "profession"
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
    }

    val json: Map<String, Any>
        get() = hashMapOf(
            ID_KEY to id,
            NAME_KEY to name,
            GRADE_KEY to grade,
            PROFESSION_KEY to PROFESSION_KEY,
            EMAIL_KEY to email,
            PASSWORD_KEY to password,
        )

}
