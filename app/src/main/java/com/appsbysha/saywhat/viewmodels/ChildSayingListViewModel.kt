package com.appsbysha.saywhat.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.removeSaying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by sharone on 14/01/2025.
 */


class ChildSayingListViewModel(app: Application) :  AndroidViewModel(app) {

    var selectedChild: Child? = null
    private val _removeSayingClick: MutableStateFlow<Saying?> = MutableStateFlow(null)
    val removeSayingClick: StateFlow<Saying?> = _removeSayingClick

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

    fun onRemoveSayingClick(saying: Saying) {
        _removeSayingClick.value = saying
    }

    fun closeRemoveSayingDialog() {
        _removeSayingClick.update { null }
    }
}