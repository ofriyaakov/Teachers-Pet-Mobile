package com.example.teacherspet.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.FieldValue

@Entity
data class Post(
    @PrimaryKey var id: String,
    var userId: String,
    var imageUri: String,
    var description: String,
    var userName: String,
    var isDeleted: Boolean,
) {

    companion object {

        const val ID_KEY = "id"
        const val USER_ID_KEY = "userId"
        const val IMAGE_URI_KEY = "imageUri"
        const val DESCRIPTION_KEY = "description"
        const val USER_NAME_KEY = "userName"
        const val IS_DELETED_KEY = "isDeleted"

        fun fromJSON(json: Map<String, Any>): Post {
            val id = json[ID_KEY] as? String ?: ""
            val userId = json[USER_ID_KEY] as? String ?: ""
            val imageUri = json[IMAGE_URI_KEY] as? String ?: ""
            val description = json[DESCRIPTION_KEY] as? String ?: ""
            val userName = json[USER_NAME_KEY] as? String ?: ""
            val isDeleted = json[IS_DELETED_KEY] as? Boolean ?: false

            return Post(
                id = id,
                userId = userId,
                imageUri = imageUri,
                description = description,
                userName = userName,
                isDeleted= isDeleted

            )
        }
    }
    val json: Map<String, Any>
        get() = hashMapOf(
            ID_KEY to id,
            USER_ID_KEY to userId,
            IMAGE_URI_KEY to imageUri,
            DESCRIPTION_KEY to description,
            USER_NAME_KEY to userName,
            IS_DELETED_KEY to isDeleted
        )

}