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
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var sayingEditViewModel: SayingEditViewModel
    private lateinit var childrenViewModel: ChildrenViewModel
    private lateinit var childSayingsViewModel: ChildSayingListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawableId: Int = R.drawable.img_yoav

        Log.d("Firebase_TEST", "fetchUserData MAIN $drawableId")

        sayingEditViewModel =
            ViewModelProvider(this)[SayingEditViewModel::class.java]
        childrenViewModel =
            ViewModelProvider(this)[ChildrenViewModel::class.java]
        childSayingsViewModel =
            ViewModelProvider(this)[ChildSayingListViewModel::class.java]
        setContent {
            childrenViewModel.fetchChildrenData()
            val navController = rememberNavController()
            //observeChildSelected()
            //observeUpdatedSaying()
            NavHost(navController, startDestination = "children") {
                composable("children") { ChildrenView(childrenViewModel, childSayingsViewModel, navController) }
                composable("childSayings/{childId}") { backStackEntry -> val childId = backStackEntry.arguments?.getString("childId") ?: return@composable
                    ChildSayingListView(childSayingsViewModel, childId, childrenViewModel, navController) }
               composable("saying/{sayingId}") {backStackEntry -> val sayingId = backStackEntry.arguments?.getString("sayingId") ?: return@composable
                   SayingEditView(sayingEditViewModel,childSayingsViewModel, sayingId, navController) }
                composable("saying") {
                    SayingEditView(sayingEditViewModel, childSayingsViewModel, null, navController) }

            }

        }
    }
}
