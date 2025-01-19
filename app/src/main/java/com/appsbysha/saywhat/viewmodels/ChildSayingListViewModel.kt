package com.appsbysha.saywhat.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.removeSaying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by sharone on 14/01/2025.
 */


class ChildSayingListViewModel(app: Application) : MainViewModel(app)  {
   // var _sayingsList : MutableStateFlow<MutableList<Saying>> = MutableStateFlow(mutableListOf())
    val  _mainChild = MutableLiveData<Child>()
    val mainChild :LiveData<Child> get() = _mainChild


    fun setChild(child: Child) { _mainChild.value = child }
    fun onAddNewSayingClick(navController: NavController) {
        navController.navigate("saying")
    }
    fun onEditSaying(item: Saying){}
    fun onDeleteSaying(item: Saying){
        viewModelScope.launch {
            mainChild.value?.let { removeSaying(it, item) }
            //update the local list! and num above - how to do
        }
    }
}