package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.listenToUserData
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.removeChild
import com.appsbysha.saywhat.uploadChildToFirebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by sharone on 13/01/2025.
 */


class ChildrenViewModel(app: Application) : MainViewModel(app) {

    private var _children: MutableStateFlow<List<Child>> = MutableStateFlow(listOf())
    val children = _children.asStateFlow()
    private val _addChildClick = MutableStateFlow(false)
    val addChildClick: StateFlow<Boolean> = _addChildClick


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

//    fun onChildClick(child: Child, navController: NavController) {
//
//        _selectedChild.value = child
//        navController.navigate("childSayingList")
//
//    }

    fun onAddChildClick() {
        _addChildClick.update { true}
    }

    fun closeAddChildDialog() {
        _addChildClick.update {  false}
    }

    fun onCreateChild(name: String, dob: Long, image: Uri?) {

        val newChild = Child(name = name, dob = dob)
        Log.d("Firebase_TEST", "add child $newChild")
        viewModelScope.launch { uploadChildToFirebase(newChild) }
    }

    fun onRemoveChild(child: Child) {

        viewModelScope.launch {
            removeChild(child)
        }
    }

}