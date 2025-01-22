package com.appsbysha.saywhat.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.removeSaying
import kotlinx.coroutines.launch

/**
 * Created by sharone on 14/01/2025.
 */


class ChildSayingListViewModel(app: Application) : MainViewModel(app) {

    var selectedChild: Child? = null

    fun setChild(child: Child) {
        selectedChild = child
    }

    fun onDeleteSaying(item: Saying) {
        viewModelScope.launch {
            selectedChild?.let {
                removeSaying(it, item)
            }
        }
    }
}