package com.appsbysha.saywhat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel

/**
 * Created by sharone on 13/01/2025.
 */


@Composable
fun ChildrenView(viewModel: ChildrenViewModel? =null, navController: NavController) {
    val childrenState = viewModel?.children?.collectAsState()
    val addChildState = viewModel?.addChildClick?.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->

            Column {

                Text(" + Add child", fontSize = 22.sp, modifier = Modifier.clickable {
                      viewModel?.onAddChildClick()
                })

                if (childrenState?.value != null) {
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.Black)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(padding)

                    ) {
                        itemsIndexed(childrenState.value) { index, item ->
                            Catalog.ChildCardView(
                                item,
                                Modifier.clickable { viewModel.onChildClick(item, navController) })
                        }


                    }
                }
            }
            if(addChildState?.value == true)
            {
                Catalog.InputDialog (onDismiss = { viewModel.closeAddChildDialog() }, onSubmit = { name, dob, uri->
                    viewModel.onCreateChild(name,dob,uri)
                })
            }
        }

    )
}

