package com.appsbysha.saywhat.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel

/**
 * Created by sharone on 12/01/2025.
 */


@Composable
fun SayingEditView(viewModel: SayingEditViewModel? = null, navController: NavController) {

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

            Catalog.Saying(padding, sayingState?.value?: listOf(), mainChildState?.value?: Child())
        }


    )


}
