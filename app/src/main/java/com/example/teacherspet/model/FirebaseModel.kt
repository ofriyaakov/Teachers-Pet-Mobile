import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.example.teacherspet.base.Constants
import com.example.teacherspet.base.EmptyCallback
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import com.example.teacherspet.model.User
import com.example.teacherspet.model.Post

class FirebaseModel {
    private val database = Firebase.firestore
    private val storage = Firebase.storage
    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })
        }
        database.firestoreSettings = settings
    }

    fun addUser(user: User, callback: EmptyCallback) {
        database.collection(Constants.Collections.USERS).document(user.id).set(user.json)
            .addOnCompleteListener {
                callback()
            }
            .addOnFailureListener {
                Log.d("TAG", it.toString() + it.message)
            }
    }

    fun addPost(post: Post, callback: EmptyCallback) {

        val storageRef: StorageReference = storage.reference

        val file = Uri.fromFile(File(post.imageUri))

        val riversRef = storageRef.child("gs://teacher-s-pet-6c42a.firebasestorage.app/images")

        val uploadTask = riversRef.putFile(file)

        val mountainsRef = storageRef.child("file.jpg")

        if (file !== null) {

            uploadTask.addOnFailureListener {
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                }
            }

            database.collection(Constants.Collections.POSTS).document(post.id).set(post.json)
                .addOnCompleteListener {
                    callback()
                }
                .addOnFailureListener {
                    Log.d("TAG", it.toString() + it.message)
                }
        }
    }
    fun uploadImage(image: Bitmap, name: String, callback: (String?) -> Unit) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/$name.jpg")
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            callback(null)
        }.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                callback(uri.toString())
            }
        }
    }
}