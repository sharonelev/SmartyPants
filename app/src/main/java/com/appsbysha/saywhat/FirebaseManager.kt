package com.appsbysha.saywhat

import android.net.Uri
import android.util.Log
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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




suspend fun listenToUserData(onUserDataChange: (User?) -> Unit) {
    val database = Firebase.database
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid

    withContext(Dispatchers.IO) {
    if (uid != null) {
        val userRef = database.getReference("users/$uid/children")

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataMap = snapshot.value as? Map<*, *>
                if(dataMap == null)
                {
                    onUserDataChange(null)
                    return
                }
                val gson = Gson()
                val jsonString = gson.toJson(dataMap)
                val type = object : TypeToken<HashMap<String, Child>>() {}.type
                val childrenMap: HashMap<String, Child> = gson.fromJson(jsonString, type)
                val user = User(children = childrenMap)
                onUserDataChange(user)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase_TEST", "Error getting data", error.toException())
                onUserDataChange(null)
            }
        })
    }
}
    }


suspend fun uploadSayingToFirebase(child: Child, saying: Saying) {
    val database = Firebase.database
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    val sayingRef =
        database.getReference("users/$uid/children").child(child.childId)
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
    val database = Firebase.database
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    val sayingRef = database.getReference("users/$uid/children")
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
    val database = Firebase.database
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    val sayingRef =
        database.getReference("users/$uid/children").child(child.childId)
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
    val database = Firebase.database
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    val childRef =
        database.getReference("users/$uid/children").child(child.childId)
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
    val storage = Firebase.storage
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    val uid = currentUser?.uid
    return withContext(Dispatchers.IO) {
        val imagesRef = storage.reference.child("users/$uid/images")
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