package com.michel.productsapp.di

import com.michel.domain.usecase.GetDataUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetDataUseCase> {
        GetDataUseCase(iUserRepository = get())
    }

}