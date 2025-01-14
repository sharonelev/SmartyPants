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
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel
import com.appsbysha.saywhat.viewmodels.MainViewModel
import com.appsbysha.saywhat.viewmodels.SayingViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sayingViewModel: SayingViewModel
    private lateinit var childrenViewModel: ChildrenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawableId: Int = R.drawable.img_yoav

        Log.d("Firebase_TEST","fetchUserData MAIN $drawableId")

        mainViewModel=
            ViewModelProvider(this)[MainViewModel::class.java]
        sayingViewModel =
            ViewModelProvider(this)[SayingViewModel::class.java]
        childrenViewModel =
            ViewModelProvider(this)[ChildrenViewModel::class.java]
        setContent {
            childrenViewModel.fetchChildrenData("sha171")
            val navController = rememberNavController()
            observeChildSelected()
            NavHost(navController, startDestination = "children") {
                composable("children") { ChildrenView(childrenViewModel, navController) }
                composable("saying") { SayingView( sayingViewModel, navController) }
            }

        }
    }


    private fun observeChildSelected(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                childrenViewModel.selectedChild.collect { selectedChild ->
                    sayingViewModel._mainChild.value = selectedChild
                    sayingViewModel._sayingList.value = selectedChild.sayings.values.firstOrNull()?.lineList?: listOf()
                }
            }
        }
    }


}
