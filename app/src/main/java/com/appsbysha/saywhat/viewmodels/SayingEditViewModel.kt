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
import kotlinx.coroutines.flow.update

/**
 * Created by sharone on 12/01/2025.
 */


class SayingEditViewModel(val app: Application) : MainViewModel(app) {

    private val TAG = "SayingEditViewModel"
    private var _lineList: MutableStateFlow<MutableList<Line>> = MutableStateFlow(mutableListOf())
    val lineList = _lineList.asStateFlow()
    var selectedChild: Child? = null

    //use new only if not edit mode:
    private var editSaying = Saying()
    private val _navigateToDetails = MutableStateFlow(false)
    val navigateToDetails: StateFlow<Boolean> = _navigateToDetails

    fun setSaying(saying: Saying?) {
        //todo save and show draft only if new saying
        Log.i(TAG, "setsaying :  ${saying}")

        editSaying = Saying()
        _lineList.value = mutableListOf()

        if (saying != null) {
            editSaying = saying.copy()
            val getLineList = saying.lineList

            _lineList.value = getLineList.map { it.copy() }.toMutableList()

        }
    }

    fun setChild(child: Child?) {
        selectedChild = child
    }

    fun updateOtherPersonName(index: Int, newText: String) {
        val updatedList = _lineList.value.toMutableList()
        updatedList[index] = updatedList[index].copy(otherPerson = newText)
        _lineList.value = updatedList
    }

    fun updateLine(index: Int, newText: String) {
        val updatedList = _lineList.value.toMutableList()
        updatedList[index] = updatedList[index].copy(text = newText)
        _lineList.value = updatedList
    }


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
        if (lineList.value.isEmpty()) {
            return
        }

        editSaying.lineList = lineList.value
        selectedChild?.let { updateSaying(it, editSaying) }
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
        editSaying.age = newValue
    }


}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LineToolBar(Child(), null)
}