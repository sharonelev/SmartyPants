package com.appsbysha.saywhat.viewmodels
import android.app.Application
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Created by sharone on 12/01/2025.
 */


class SayingEditViewModel(app: Application) : MainViewModel(app) {

    var _lineList :MutableStateFlow<MutableList<Line>> = MutableStateFlow(mutableListOf())
    val lineList = _lineList.asStateFlow()
    var _mainChild :MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()


    fun onAddLineClick(lineType: LineType){
        val currentList = lineList.value.toMutableList()
        currentList.add(Line(lineType = lineType, text = "Enter here"))
        _lineList.value = currentList
    }



}
