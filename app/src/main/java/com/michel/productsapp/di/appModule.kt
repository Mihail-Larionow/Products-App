package com.michel.productsapp.di

import com.michel.productsapp.data.api.ProductAPI
import com.michel.productsapp.data.api.ProductAPIClient
import com.michel.productsapp.data.repository.ProductsRepository
import com.michel.productsapp.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// The required dependencies in the application module are described here
val appModule = module {

    viewModel<MainViewModel>{
        MainViewModel(
            productsRepository = get()
        )
    }

    single<ProductsRepository>{
        ProductsRepository(
            productAPI = get()
        )
    }

    single<ProductAPI>{
        ProductAPIClient().getClient()
    }

}