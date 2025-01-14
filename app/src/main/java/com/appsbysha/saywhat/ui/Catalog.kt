package com.appsbysha.saywhat.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsbysha.saywhat.R
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel


/**
 * Created by sharone on 29/11/2024.
 */


object Catalog {


    @Composable
    fun Saying(
        paddingValues: PaddingValues,
        listOfLines: List<Line>,
        mainChild: Child,
        editMode: Boolean,
        onRemoveClick: (line: Line) -> Unit? = {},
    ) {
        var isExpanded by remember { mutableStateOf(false) }

        LazyColumn(modifier = Modifier
            .background(Color.Cyan)
            .clickable { isExpanded = !isExpanded }
            .heightIn(max = if (isExpanded || editMode) 1000.dp else 100.dp)
            .padding(paddingValues)

        ) {
            itemsIndexed(listOfLines) { index, item ->
                Sentence(
                    line = item,
                    imgResource = mainChild.profilePic,
                    otherPersonName = item.otherPerson,
                    editMode = editMode,
                    onRemoveClick
                )
            }
        }
    }

    @Composable
    fun Sentence(
        line: Line,
        imgResource: Int? = null,
        otherPersonName: String? = null,
        editMode: Boolean,
        onRemoveClick: (line: Line) -> Unit? = {},
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),

            horizontalArrangement = when (line.lineType) {
                LineType.OTHER_PERSON -> {
                    Arrangement.End
                }

                LineType.NOTE -> {
                    Arrangement.Center
                }

                else -> {//LineType.MAIN_CHILD
                    Arrangement.Start
                }
            }
        ) {
            if (editMode) {
                Image(painter = painterResource(R.drawable.remove_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(26.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onRemoveClick(line) })
            }
            if (line.lineType == LineType.MAIN_CHILD && imgResource != null) {

                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .align(Alignment.Bottom)
                ) {
                    Image(
                        painter = painterResource(imgResource),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)


                    )
                }

            }
            SpeechBubble(
                line = line, otherPersonName = otherPersonName, editMode = editMode
            )

        }
    }

    @Composable
    fun SpeechBubble(
        line: Line,
        otherPersonName: String? = null,
        editMode: Boolean,
        onLineUpdated: (line: Line) -> Unit? = {},
    ) {

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (line.lineType == LineType.MAIN_CHILD) 0f else 48f,
                        bottomEnd = if (line.lineType == LineType.OTHER_PERSON) 0f else 48f
                    )
                )
                .background(Color.Green)
                .padding(12.dp)
        ) {
            Column {
                if (line.lineType == LineType.OTHER_PERSON) {
                    if (editMode) {
                        var nameText by remember { mutableStateOf(otherPersonName ?: "") }
                        TextField(value = nameText, onValueChange = {
                            nameText = it
                            line.otherPerson = it
                            onLineUpdated(line)
                        }, label = { Text("Enter name here") })
                    } else {
                        if (otherPersonName != null) {
                            Text(text = otherPersonName, fontSize = 12.sp)
                        }
                    }
                }
                if (editMode) {
                    var speechText by remember { mutableStateOf("") }
                    TextField(value = speechText, onValueChange = {
                        speechText = it
                        line.text = it
                        onLineUpdated(line)
                    }, label = { Text("Enter text here") })
                } else {
                    Text(text = line.text, fontSize = 16.sp)
                }
            }
        }
    }

    @Composable
    fun ChildCardView(child: Child, modifier: Modifier) {
        Card(
            shape = RoundedCornerShape(8.dp), modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                if (child.profilePic != null) {
                    Image(
                        painter = painterResource(child.profilePic),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = child.name, style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = child.dob.toString(), style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = "numOfSayings ${child.sayings.size.toString()}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }


    @Composable
    fun LineToolBar(child: Child?, viewModel: SayingEditViewModel) {
        Row {
            if (child?.profilePic != null) {
                Image(painter = painterResource(child.profilePic),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.onAddLineClick(lineType = LineType.MAIN_CHILD) })
            }
            Image(painter = painterResource(R.drawable.speech_bubble_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .graphicsLayer(scaleX = -1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel.onAddLineClick(lineType = LineType.OTHER_PERSON) }

            )
            Image(painter = painterResource(R.drawable.note_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel.onAddLineClick(lineType = LineType.NOTE) }

            )
            Image(painter = painterResource(R.drawable.save_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel.onSaveClick() }

            )
            Image(painter = painterResource(R.drawable.remove_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { viewModel.onRemoveAllClick() }

            )
        }
    }
}



