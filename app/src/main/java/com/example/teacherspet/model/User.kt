package com.example.teacherspet.model

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.example.teacherspet.base.TeachersPetApplication

@Entity
data class User(
    @PrimaryKey val id: String,
    val name: String,
    val grade: String,
    val profession: String,
    val email: String,
    val password: String,
    val lastUpdated: Long? = null
) {

    companion object {
        var lastUpdated: Long
            get() = TeachersPetApplication.Globals.context?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                ?.getLong(LOCAL_LAST_UPDATED, 0) ?: 0

            set(value) {
                TeachersPetApplication.Globals.context
                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.apply {
                        edit().putLong(LOCAL_LAST_UPDATED, value).apply()
                    }
            }


        const val ID_KEY = "id"
        const val NAME_KEY = "name"
        const val GRADE_KEY = "grade"
        const val PROFESSION_KEY = "profession"
        const val EMAIL_KEY = "email"
        const val PASSWORD_KEY = "password"
        const val LAST_UPDATED = "lastUpdated"
        const val LOCAL_LAST_UPDATED = "locaStudentLastUpdated"

        fun fromJSON(json: Map<String, Any>): User {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val grade = json[GRADE_KEY] as? String ?: ""
            val profession = json[PROFESSION_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String ?: ""
            val password = json[PASSWORD_KEY] as? String ?: ""
            val timeStamp = json[LAST_UPDATED] as? Timestamp
            val lastUpdatedLongTimestamp = timeStamp?.toDate()?.time
            return User(
                id = id,
                name = name,
                grade = grade,
                profession = profession,
                email = email,
                password = password,
                lastUpdated = lastUpdatedLongTimestamp
            )
        }
    }

    val json: Map<String, Any>
        get() = hashMapOf(
            ID_KEY to id,
            NAME_KEY to name,
            GRADE_KEY to grade,
            PROFESSION_KEY to PROFESSION_KEY,
            EMAIL_KEY to email,
            PASSWORD_KEY to password,
            LAST_UPDATED to FieldValue.serverTimestamp()
        )

}
