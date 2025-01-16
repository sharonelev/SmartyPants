package com.appsbysha.saywhat.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.model.Saying
import com.appsbysha.saywhat.ui.Catalog.LineToolBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter

/**
 * Created by sharone on 12/01/2025.
 */


class SayingEditViewModel(val app: Application) : MainViewModel(app) {

    var _lineList: MutableStateFlow<MutableList<Line>> = MutableStateFlow(mutableListOf())
    val lineList = _lineList.asStateFlow()
    var _mainChild: MutableStateFlow<Child> = MutableStateFlow(Child())
    val mainChild = _mainChild.asStateFlow()
    private val _saveSaying = MutableStateFlow(Saying())
    val saveSaying: StateFlow<Saying> = _saveSaying
    private val _navigateToDetails = MutableStateFlow(false)
    val navigateToDetails: StateFlow<Boolean> = _navigateToDetails


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

    fun onNavigationDone() {
        _navigateToDetails.value = false
    }

    fun onSaveClick() {
        if(lineList.value.isEmpty()){
            return
        }
        Log.i("SayingEditViewModel", "on save click ${lineList.value}")
        val newSaying = Saying(lineList = lineList.value, age = saveSaying.value.age)
       // newSaying.lineList = lineList.value
        _saveSaying.value = newSaying
        onRemoveAllClick()
        Toast.makeText(app, "Saying Saved!", Toast.LENGTH_SHORT).show()
        _navigateToDetails.value = true

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

    fun onAgeUpdated(newValue: Float) {
        _saveSaying.value.age = newValue
    }


}
@Preview(showBackground = true)
@Composable
fun Preview() {
    LineToolBar(Child(), null)
}