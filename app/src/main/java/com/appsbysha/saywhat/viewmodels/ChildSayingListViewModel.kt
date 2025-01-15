package com.appsbysha.saywhat.viewmodels

import android.app.Application
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.Saying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 14/01/2025.
 */


class ChildSayingListViewModel(app: Application) : MainViewModel(app)  {
    var _sayingsList : MutableStateFlow<MutableList<Saying>> = MutableStateFlow(mutableListOf())
    val sayingsList = _sayingsList.asStateFlow()
    var _mainChild : MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()


    fun onAddNewSayingClick(navController: NavController) {
        navController.navigate("saying")
    }
    fun onEditSaying(){}
    fun onDeleteSaying(){}
}