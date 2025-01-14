package com.appsbysha.saywhat.viewmodels
import android.app.Application
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 12/01/2025.
 */


class SayingViewModel(app: Application) : MainViewModel(app) {

    var _sayingList :MutableStateFlow<List<Line>> = MutableStateFlow(listOf())
    val sayingList = _sayingList.asStateFlow()
    var _mainChild :MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()



}
