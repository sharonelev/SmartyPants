package com.appsbysha.saywhat.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.LineType


/**
 * Created by sharone on 29/11/2024.
 */


object Catalog {

    @Composable
    fun Sentence(
        lineType: LineType,
        text: String,
        imgResource: Int? = null,
        otherPersonName :String? = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),

            horizontalArrangement = when (lineType) {
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
            if (lineType == LineType.MAIN_CHILD && imgResource != null) {

                Box(modifier = Modifier.padding(end = 10.dp).align(Alignment.Bottom)) {
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
            SpeechBubble(text = text, lineType = lineType, otherPersonName = otherPersonName)

        }
    }

    @Composable
    fun SpeechBubble(
        bubbleColor: Color = Color.LightGray,
        textColor: Color = Color.Black,
        lineType: LineType,
        text: String,
        otherPersonName: String? = null
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
                .padding(12.dp)
        ) {
            Column {
            if(otherPersonName!=null)
            {
                Text(text = otherPersonName, fontSize = 12.sp)
            }
            Text(text = text, fontSize = 16.sp)
        }
    }
    }



    @Composable
    fun ChildCardView(child: Child, modifier: Modifier){
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if(child.profilePic != null) {
                    Image(
                        painter = painterResource(child.profilePic),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = child.name,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = child.dob.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }


}