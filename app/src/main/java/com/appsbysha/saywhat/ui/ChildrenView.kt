package com.appsbysha.saywhat.ui

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel

/**
 * Created by sharone on 13/01/2025.
 */


@Composable
fun ChildrenView(
    viewModel: ChildrenViewModel,
    childSayingListViewModel: ChildSayingListViewModel,
    navController: NavController,
) {
    val childrenState by viewModel.children.collectAsState()
    val addChildState = viewModel.addChildClick.collectAsState()
    val editChildState = viewModel.editChildClick.collectAsState()
    val removeChildState = viewModel.removeChildClick.collectAsState()




    Log.i("ChildrenView", "#### create ChildrenView")

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
                    viewModel.onAddChildClick()
                })

                LazyColumn(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(padding)

                ) {
                    itemsIndexed(childrenState) { index, item ->
                        Catalog.ChildCardView(
                            item,
                            Modifier.clickable {
                                childSayingListViewModel.setChild(item)
                                navController.navigate("childSayings/${item.childId}")
                            }
                        ,{
                            viewModel.onEditChildClick(item)
                        }, {
                            viewModel.onRemoveChildClick(item)

                        })
                    }


                }
            }
            if (addChildState.value) {
                Catalog.InputDialog(
                    null,
                    onDismiss = { viewModel.closeAddChildDialog() },
                    onSubmit = { _, name, dob, image ->
                        viewModel.onCreateChild(name, dob, image)
                    })
            }

            removeChildState.value?.let {
                Catalog.ConfirmRemoveDialog(it.name, {
                    viewModel.onRemoveChild(it)
                    viewModel.closeRemoveChildDialog()
                },
                    { viewModel.closeRemoveChildDialog() })
            }

            editChildState.value?.let {
                Catalog.InputDialog(
                    it,
                    onDismiss = { viewModel.closeEditChildDialog() },
                    onSubmit = { id, name, dob, image ->
                        id?.let {childId->
                            viewModel.onUpdateChild(childId, name, dob, image)
                        }                   }
                )}

        }

    )
}

