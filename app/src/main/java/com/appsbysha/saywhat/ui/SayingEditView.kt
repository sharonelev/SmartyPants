package com.appsbysha.saywhat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel

/**
 * Created by sharone on 12/01/2025.
 */


@Composable
fun SayingEditView(viewModel: SayingEditViewModel, childSayingListViewModel: ChildSayingListViewModel, sayingId: String?, navController: NavController) {

    val lineListState = viewModel.lineList.collectAsState()
    viewModel.setSaying(childSayingListViewModel.selectedChild?.sayings?.values?.firstOrNull{ it.id == sayingId })


    val child = childSayingListViewModel.selectedChild
    viewModel.setChild(child)

    val navigateToDetails by viewModel.navigateToDetails.collectAsState()

    if (navigateToDetails) {
        navController.popBackStack()
        viewModel.onNavigationDone() // Reset the navigation state
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->

            Column(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Catalog.LineToolBar(child!!, viewModel)
                }

                Catalog.Saying(
                    padding,
                    lineListState.value,
                    child!!,
                    true,
                    viewModel = viewModel
                )

            }
        }

    )


}
