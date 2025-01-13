package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.appsbysha.saywhat.getUserData
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.User
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by sharone on 13/01/2025.
 */


class ChildrenViewModel(app: Application) : MainViewModel(app) {

    var _children : MutableStateFlow<List<Child>> = MutableStateFlow(listOf())
    val children = _children.asStateFlow()



    fun fetchChildrenData(userId: String) {
        viewModelScope.launch {
            try {
                val userDeferredResult = async {
                    getUserData(userId)
                }
                val user = userDeferredResult.await()
                // Handle the user data
                Log.d("Firebase_TEST", "User data: $user")
                _children.value = user?.children?.map { it.value }?.toList() ?: listOf()
            } catch (e: Exception) {
                Log.e("Firebase_TEST", "Network alert!")
                _children.value = emptyList()
            }
        }
    }

    fun onChildClick(navController: NavController, childId: String){
        navController.navigate("saying")
    }
    
}