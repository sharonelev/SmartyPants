package com.appsbysha.saywhat.viewmodels

import android.app.Application
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Saying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 12/01/2025.
 */


class SayingViewModel(app: Application) : MainViewModel(app) {

    private var _saying :MutableStateFlow<MutableList<Saying>> = MutableStateFlow(mutableListOf())
    val saying = _saying.asStateFlow()
    private var _mainChild :MutableStateFlow<Child?> = MutableStateFlow(null)
    val mainChild = _mainChild.asStateFlow()



}