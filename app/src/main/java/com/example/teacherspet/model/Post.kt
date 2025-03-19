package com.example.teacherspet.model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Post(
    @PrimaryKey val id: String,
    val userId: String,
    val imageUri: String,
    val description: String,
) {

    companion object {

        const val ID_KEY = "id"
        const val USER_ID_KEY = "userId"
        const val IMAGE_URI_KEY = "imageUri"
        const val DESCRIPTION_KEY = "description"

        fun fromJSON(json: Map<String, Any>): Post {
            val id = json[ID_KEY] as? String ?: ""
            val userId = json[USER_ID_KEY] as? String ?: ""
            val imageUri = json[IMAGE_URI_KEY] as? String ?: ""
            val description = json[DESCRIPTION_KEY] as? String ?: ""

            return Post(
                id = id,
                userId = userId,
                imageUri = imageUri,
                description = description
            )
        }
    }
    val json: Map<String, Any>
        get() = hashMapOf(
            ID_KEY to id,
            USER_ID_KEY to userId,
            IMAGE_URI_KEY to imageUri,
            DESCRIPTION_KEY to description,
        )
}