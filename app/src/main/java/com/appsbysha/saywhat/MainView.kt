package com.appsbysha.saywhat

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.tooling.preview.Preview
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.ui.Catalog

/**
 * Created by sharone on 29/11/2024.
 */



@Composable
fun MainView() {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxHeight()
            .fillMaxWidth()

    ) {

        Catalog().Sentence(LineType.MAIN_CHILD, text = "Hello world!")
        Catalog().Sentence(LineType.OTHER_PERSON,text = "Hello world!")
        Catalog().Sentence(LineType.NOTE,text = "Hello world!")


    }


}


@Preview(showBackground = true)
@Composable
fun Preview(){
    MainView()
}