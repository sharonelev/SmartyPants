package com.appsbysha.saywhat

import android.net.Uri
import android.util.Log
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.coroutines.resumeWithException


/**
 * Created by sharone on 07/01/2025.
 */


suspend fun saveUserData(userId: String, user: User) {
    val database = Firebase.database
    val userRef = database.getReference("users").child(userId)
    val gson = Gson()
    val jsonString = gson.toJson(user)
    userRef.setValue(jsonString).await()
}


suspend fun listenToUserData(userId: String, onUserDataChange: (User?) -> Unit) {
    val database = Firebase.database

    withContext(Dispatchers.IO) {
        val userRef = database.getReference("users").child(userId)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = snapshot.value as? Map<*, *>
                val gson = Gson()
                val jsonString = gson.toJson(dataMap)
                val user = gson.fromJson(jsonString, User::class.java)
                onUserDataChange(user)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase_TEST", "Error getting data", error.toException())
                onUserDataChange(null)
            }
        })
    }
}


suspend fun uploadSayingToFirebase(child: Child, saying: Saying) {
    val database = FirebaseDatabase.getInstance()
    val sayingRef =
        database.getReference("users").child("sha171").child("children").child(child.childId)
            .child("sayings")
    val updates = hashMapOf<String, Any>(
        saying.id to saying
    )
    withContext(Dispatchers.IO) {
        sayingRef.updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase_TEST", "Update successful")
            } else {
                Log.d("Firebase_TEST", "Update failed", task.exception)
            }
        }
    }
}

suspend fun uploadChildToFirebase(child: Child) {
    val database = FirebaseDatabase.getInstance()
    val sayingRef = database.getReference("users").child("sha171").child("children")
    val updates = hashMapOf<String, Any>(
        child.childId to child
    )
    withContext(Dispatchers.IO) {
        sayingRef.updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase_TEST", "Update successful")
            } else {
                Log.d("Firebase_TEST", "Update failed", task.exception)
            }
        }
    }
}

suspend fun removeSaying(child: Child, saying: Saying) {
    val database = FirebaseDatabase.getInstance()
    val sayingRef =
        database.getReference("users").child("sha171").child("children").child(child.childId)
            .child("sayings").child(saying.id)
    withContext(Dispatchers.IO) {
        sayingRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase_TEST", "remove saying successful")
            } else {
                Log.d("Firebase_TEST", "remove saying failed", task.exception)
            }
        }
    }
}

suspend fun removeChild(child: Child) {
    val database = FirebaseDatabase.getInstance()
    val childRef =
        database.getReference("users").child("sha171").child("children").child(child.childId)
    withContext(Dispatchers.IO) {
        childRef.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase_TEST", "remove child successful")
            } else {
                Log.d("Firebase_TEST", "remove child failed", task.exception)
            }
        }
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
suspend fun uploadImageToFirebase(imageUri: Uri): Uri? {
    return withContext(Dispatchers.IO) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("sha171").child("images") // Create a "images" folder
        val imageRef = imagesRef.child("${UUID.randomUUID()}.jpg") // Specify the image name
        val uploadTask = imageRef.putFile(imageUri)

        val downloadUrl = async {
            suspendCancellableCoroutine<Uri?> { continuation ->
                uploadTask.addOnSuccessListener { taskSnapshot ->
                    // Image uploaded successfully
                    // Get the download URL
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                        continuation.resume(uri) { }
                    }.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
            }
        }.await()

        downloadUrl
    }
}