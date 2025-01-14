package com.appsbysha.saywhat.viewmodels

import android.app.Application
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.Saying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 14/01/2025.
 */


class ChildSayingListViewModel(app: Application) : MainViewModel(app)  {
    var _sayingsList : MutableStateFlow<List<Saying>> = MutableStateFlow(listOf())
    val sayingsList = _sayingsList.asStateFlow()
    var _mainChild : MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()
}