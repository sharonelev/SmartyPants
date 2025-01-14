package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.listenToUserData
import com.appsbysha.saywhat.model.Child
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by sharone on 13/01/2025.
 */


class ChildrenViewModel(app: Application) : MainViewModel(app) {

    var _children: MutableStateFlow<List<Child>> = MutableStateFlow(listOf())
    val children = _children.asStateFlow()
    private val _selectedChild = MutableStateFlow(Child())
    val selectedChild: StateFlow<Child> = _selectedChild

    fun fetchChildrenData(userId: String) {
        viewModelScope.launch {
            try {

                listenToUserData(userId) { user ->
                    if (user != null) {
                        // Update your application state with the new user data
                        Log.d("Firebase_TEST", "User data: $user")
                        _children.value = user.children.map { it.value }.toList()
                    } else {
                        Log.e("Firebase_TEST", "Network alert!")
                        _children.value = emptyList()
                    }


                }
            } catch (e: Exception) {
                Log.e("Firebase_TEST", "Network alert!")
                _children.value = emptyList()
            }

        }

    }

    fun onChildClick(child: Child, navController: NavController) {
        _selectedChild.value = child
        navController.navigate("saying")

    }

}