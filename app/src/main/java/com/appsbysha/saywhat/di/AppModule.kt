package com.appsbysha.saywhat.di

import com.appsbysha.saywhat.viewmodels.ChildSayingListViewModel
import com.appsbysha.saywhat.viewmodels.ChildrenViewModel
import com.appsbysha.saywhat.viewmodels.SayingEditViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by sharone on 28/07/2024.
 */
val appModule = module {
    single { }
    viewModel {
        SayingEditViewModel(androidApplication())
        ChildrenViewModel(androidApplication())
        ChildSayingListViewModel(androidApplication())

    }
}