package com.michel.productsapp.di

import com.michel.productsapp.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<MainViewModel>{
        MainViewModel(
            loadDataUseCase = get()
        )
    }

}