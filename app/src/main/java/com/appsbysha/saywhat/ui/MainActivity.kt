package com.appsbysha.saywhat.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsbysha.saywhat.R
import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel
import com.appsbysha.saywhat.viewmodels.MainViewModel
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sayingEditViewModel: SayingEditViewModel
    private lateinit var childrenViewModel: ChildrenViewModel
    private lateinit var childSayingsViewModel: ChildSayingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawableId: Int = R.drawable.img_yoav

        Log.d("Firebase_TEST","fetchUserData MAIN $drawableId")

        mainViewModel=
            ViewModelProvider(this)[MainViewModel::class.java]
        sayingEditViewModel =
            ViewModelProvider(this)[SayingEditViewModel::class.java]
        childrenViewModel =
            ViewModelProvider(this)[ChildrenViewModel::class.java]
        childSayingsViewModel =
            ViewModelProvider(this)[ChildSayingListViewModel::class.java]
        setContent {
            childrenViewModel.fetchChildrenData("sha171")
            val navController = rememberNavController()
            observeChildSelected()
            NavHost(navController, startDestination = "children") {
                composable("children") { ChildrenView(childrenViewModel, navController) }
                composable("saying") { SayingEditView( sayingEditViewModel, navController) }
                composable("childSayingList") { ChildSayingListView( childSayingsViewModel, navController) }
            }

        }
    }


    private fun observeChildSelected(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                childrenViewModel.selectedChild.collect { selectedChild ->
                    childSayingsViewModel._mainChild.value = selectedChild
                    childSayingsViewModel._sayingsList.value = selectedChild.sayings.values.toList()
                }
            }
        }
    }


}
