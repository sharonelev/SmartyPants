package com.appsbysha.saywhat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.model.Line
import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel

/**
 * Created by sharone on 14/01/2025.
 */


@Composable
fun ChildSayingListView(viewModel: ChildSayingListViewModel? =null, navController: NavController) {
    val sayingsListState = viewModel?.sayingsList?.collectAsState()
    val mainChildState = viewModel?.mainChild?.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->

            if (sayingsListState?.value != null) {


                Column {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                viewModel.onAddNewSayingClick(navController)
                            }
                    ) {

                        Text("+ Add new Saying NUM: ${mainChildState?.value?.sayings?.size}", fontSize = 20.sp)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(padding)

                    ) {

                        itemsIndexed(sayingsListState.value) { index, item ->

                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Column {
                                    Row {
                                        Text(text = "Age: ${item.age}")
                                        Text(text = "    Remove    ", modifier = Modifier.clickable { viewModel.onDeleteSaying(item) })
                                        Text(text = "    Edit    ", modifier = Modifier.clickable { viewModel.onEditSaying(item) })

                                    }
                                Catalog.Saying(
                                    padding,
                                    item.lineList,
                                    mainChildState?.value ?: Child(),
                                    editMode = false
                                )
                            }
                        }
                        }
                    }
                }
            }
        }
    )
}

