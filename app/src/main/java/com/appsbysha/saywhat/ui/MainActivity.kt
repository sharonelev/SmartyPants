package com.appsbysha.saywhat.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel
import com.appsbysha.saywhat.viewmodels.MainViewModel
import com.appsbysha.saywhat.viewmodels.SayingViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainViewModel: MainViewModel =
            ViewModelProvider(this)[MainViewModel::class.java]
        val sayingViewModel: SayingViewModel =
            ViewModelProvider(this)[SayingViewModel::class.java]
        val childrenViewModel: ChildrenViewModel =
            ViewModelProvider(this)[ChildrenViewModel::class.java]
        Log.d("Firebase_TEST","fetchUserData MAIN")


       // mainViewModel.writeToDB()
       // mainViewModel.saveUser("sha171", userSha171)
      //  mainViewModel.updateSaying()
     //   childrenViewModel._children.value = mainViewModel.fetchUserData("sha171")?.children?.map { it.value }?.toList() ?: listOf()

        setContent {

            childrenViewModel.fetchChildrenData("sha171")
           val navController = rememberNavController()
            NavHost(navController, startDestination = "children") {
                composable("children") { ChildrenView(childrenViewModel, navController) }
                composable("saying") { SayingView( sayingViewModel, navController) }
            }
        }
    }


}