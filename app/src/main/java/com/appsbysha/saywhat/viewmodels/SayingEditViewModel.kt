package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.util.Log
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter

/**
 * Created by sharone on 12/01/2025.
 */


class SayingEditViewModel(app: Application) : MainViewModel(app) {

    var _lineList: MutableStateFlow<MutableList<Line>> = MutableStateFlow(mutableListOf())
    val lineList = _lineList.asStateFlow()
    var _mainChild: MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()
    private val _saveSaying = MutableStateFlow(Saying())
    val saveSaying: StateFlow<Saying> = _saveSaying


    fun onAddLineClick(lineType: LineType) {
        val currentList = lineList.value.toMutableList()
        val newLine = Line(lineType = lineType, text = "")
        if (lineType == LineType.OTHER_PERSON) {
            newLine.otherPerson =
                lineList.value.lastOrNull { it.lineType == LineType.OTHER_PERSON }?.otherPerson
        }
        currentList.add(newLine)
        _lineList.value = currentList
        Log.i("SayingEditViewModel", "onAddLineClick ${lineList.value}")

    }

    fun onSaveClick() {
        Log.i("SayingEditViewModel", "on save click ${lineList.value}")
        val newSaying = Saying(lineList = lineList.value)
 /*       val currentList =  _mainChild.value
        currentList.sayings[newSaying.id] = newSaying
        _mainChild.value = currentList*/
        _saveSaying.value = newSaying
    }

    fun onRemoveLine(line: Line) {
        val currentList = lineList.value.toMutableList()
        currentList.remove(line)
        _lineList.value = currentList
        Log.i("SayingEditViewModel", "onRemoveLine ${lineList.value}")

    }

    fun onRemoveAllClick() {
        _lineList.value = mutableListOf()
    }

}
