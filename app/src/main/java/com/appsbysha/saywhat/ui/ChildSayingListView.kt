package com.appsbysha.saywhat.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appsbysha.saywhat.model.Child
import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel

/**
 * Created by sharone on 14/01/2025.
 */


@Composable
fun ChildSayingListView(viewModel: ChildSayingListViewModel, childId: String, childrenViewModel: ChildrenViewModel, navController: NavController) {
    Log.i("ChildSayingListView", "#### create ChildSayingListView")

    val children by childrenViewModel.children.collectAsState()
    val child = children.firstOrNull { it.childId == childId }
    child?.let { viewModel.setChild(it) }
    //val sayings by viewModel.sayings.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Smarty Pants\n${child?.name}") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { padding ->
                Column {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                navController.navigate("saying")
                            }
                    ) {

                        Text("+ Add new Saying NUM: ${child?.sayings?.size}", fontSize = 20.sp)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(padding)

                    ) {

                        itemsIndexed(child?.sayings?.values?.toList()?: emptyList()) { index, item ->

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
                                        Text(text = "    Edit    ", modifier = Modifier.clickable {  navController.navigate("saying/${item.id}")})

                                    }
                                Catalog.Saying(
                                    padding,
                                    item.lineList,
                                    child?:Child() ,
                                    editMode = false
                                )
                            }
                        }
                        }
                    }
                }

        }
    )
}

