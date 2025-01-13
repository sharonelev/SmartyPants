package com.appsbysha.saywhat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.appsbysha.saywhat.R
import com.appsbysha.saywhat.model.LineType
import com.appsbysha.saywhat.viewmodels.SayingViewModel

/**
* Created by sharone on 12/01/2025.
*/


@Composable
fun SayingView(viewModel: SayingViewModel? =null, navController: NavController) {

    val sayingState = viewModel?.saying?.collectAsState()
    val mainChildState = viewModel?.mainChild?.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->

            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(padding)

            ) {
                Catalog.Sentence(LineType.NOTE,text = "Thxxxxis is a note")

                Catalog.Sentence(LineType.MAIN_CHILD, text = "Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world!  Hello world! Hello world! Hello world!", imgResource = R.drawable.img_yoav)
                Catalog.Sentence(LineType.OTHER_PERSON,text = "Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world! Hello world!", otherPersonName = "Daddy")


            }
        }



    )





}


@Preview(showBackground = true)
@Composable
fun Preview2(){
    //SayingView()
}