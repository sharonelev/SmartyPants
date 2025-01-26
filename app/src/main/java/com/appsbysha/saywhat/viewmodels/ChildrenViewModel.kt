package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.listenToUserData
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.removeChild
import com.appsbysha.saywhat.uploadChildToFirebase
import com.appsbysha.saywhat.uploadImageToFirebase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by sharone on 13/01/2025.
 */


class ChildrenViewModel(app: Application) : AndroidViewModel(app) {

    private var _children: MutableStateFlow<List<Child>> = MutableStateFlow(listOf())
    val children = _children.asStateFlow()
    private val _addChildClick = MutableStateFlow(false)
    val addChildClick: StateFlow<Boolean> = _addChildClick
    private val _editChildClick: MutableStateFlow<Child?> = MutableStateFlow(null)
    val editChildClick: StateFlow<Child?> = _editChildClick
    private val _removeChildClick: MutableStateFlow<Child?> = MutableStateFlow(null)
    val removeChildClick: StateFlow<Child?> = _removeChildClick

    fun fetchChildrenData(userId: String) {
        viewModelScope.launch {
            try {
                listenToUserData(userId) { user ->
                    if (user != null) {
                        // Update your application state with the new user data
                        Log.d("Firebase_TEST", "User data: $user")
                        _children.update {
                            user.children.map { it.value }.toList()
                        }
                    } else {
                        Log.e("Firebase_TEST", "Network alert!")
                        _children.update { emptyList() }
                    }
                }
            } catch (e: Exception) {
                Log.e("Firebase_TEST", "Network alert!")
                _children.update { emptyList() }
            }

        }

    }

    fun onAddChildClick() {
        _addChildClick.update { true }
    }

    fun closeAddChildDialog() {
        _addChildClick.update { false }
    }

    fun onEditChildClick(child: Child) {
        _editChildClick.value = child
    }

    fun closeEditChildDialog() {
        _editChildClick.update { null }
    }

    fun onRemoveChildClick(child: Child) {
        _removeChildClick.value = child
    }

    fun closeRemoveChildDialog() {
        _removeChildClick.update { null }
    }

    /*

        fun onCreateChild(name: String, dob: Long, image: Uri?) {

            val newChild = Child(
                name = name, dob = dob, profilePic = if (image != null) {
                    "https://cdn.shopify.com/s/files/1/0272/0202/7618/files/S24-OMG-Core-Dolls.jpg?v=1705346795"//image.toString()
                } else {
                    null
                }
            )
            Log.d("Firebase_TEST", "add child $newChild")
            viewModelScope.launch { uploadChildToFirebase(newChild) }
        }
    */


    fun onUpdateChild(child: Child?, name: String, dob: Long, image: Uri?) {

        viewModelScope.launch {
            var imageFirebaseUri: String? = image.toString()
                if (child != null && image.toString() != child.profilePic) {//update mode and new image uploaded
                    val uriDeferred = async {
                        Log.d("Firebase_TEST", "uri deferred $image")
                        uploadImageFetchUri(image)
                    }
                   imageFirebaseUri = uriDeferred.await()
                }

            Log.d("Firebase_TEST", "uri deferred $imageFirebaseUri")

            val updatedChild = if (child == null) {
                Child(
                    name = name, dob = dob, profilePic = imageFirebaseUri
                )
            } else {
                Child(
                    childId = child.childId,
                    name = name,
                    dob = dob,
                    profilePic = imageFirebaseUri,
                    sayings = child.sayings
                )
            }
            Log.d("Firebase_TEST", "update child $updatedChild")
            uploadChildToFirebase(updatedChild)
        }
    }


    private suspend fun uploadImageFetchUri(image: Uri?): String? {
        return image?.let {
            uploadImageToFirebase(it).toString()
        }
    }


    fun onRemoveChild(child: Child) {

        viewModelScope.launch {
            removeChild(child)
        }
    }

}