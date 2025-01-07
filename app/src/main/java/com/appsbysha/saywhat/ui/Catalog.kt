package com.appsbysha.saywhat.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.appsbysha.saywhat.model.LineType


/**
 * Created by sharone on 29/11/2024.
 */


class Catalog {


    @Composable
    fun Sentence( lineType: LineType,
                  text: String){
        Row(modifier = Modifier.fillMaxWidth().padding(all = 10.dp),
           horizontalArrangement = when (lineType) {
               LineType.OTHER_PERSON -> { Arrangement.End }
               LineType.NOTE -> {Arrangement.Center}
               else -> {//LineType.MAIN_CHILD
                   Arrangement.Start
               }
           }
        ) {
            SpeechBubble(lineType = lineType, text = text)
        }
    }
    @Composable
    fun SpeechBubble(
        bubbleColor: Color = Color.LightGray,
        textColor: Color = Color.Black,
        lineType: LineType,
        text: String
    ) {
        Box(
            modifier = Modifier
                //.align(if (mainChild) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (lineType == LineType.MAIN_CHILD) 0f else 48f,
                        bottomEnd = if (lineType == LineType.OTHER_PERSON) 0f else 48f
                    )
                )
                .background(Color.Green)
                .padding(16.dp)
        ) {
            Text(text = text)
        }

    }


}