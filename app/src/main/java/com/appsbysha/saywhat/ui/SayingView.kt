package com.appsbysha.saywhat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.appsbysha.saywhat.viewmodels.SayingViewModel

/**
 * Created by sharone on 12/01/2025.
 */


@Composable
fun SayingView(viewModel: SayingViewModel? = null, navController: NavController) {

    val sayingState = viewModel?.sayingList?.collectAsState()
    val mainChildState = viewModel?.mainChild?.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->

            LazyColumn(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(padding)

            ) {
                itemsIndexed(sayingState?.value ?: listOf()) { index, item ->
                    Catalog.Sentence(
                        lineType = item.lineType,
                        text = item.text,
                        imgResource = mainChildState?.value?.profilePic,
                        otherPersonName = item.otherPerson
                    )
                }
            }
        }


    )


}
