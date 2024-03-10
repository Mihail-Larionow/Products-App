package com.michel.productsapp.di

import com.michel.data.network.INetworkApi
import com.michel.data.network.NetworkApi
import com.michel.data.repository.UserRepository
import com.michel.domain.repository.IUserRepository
import org.koin.dsl.module

val dataModule = module {

    single<IUserRepository> {
        UserRepository(iNetworkApi = get())
    }

    single<INetworkApi>{
        NetworkApi()
    }

}