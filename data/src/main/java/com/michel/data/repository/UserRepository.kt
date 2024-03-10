package com.michel.data.repository

import com.michel.data.network.INetworkApi
import com.michel.domain.model.DataModel
import com.michel.domain.repository.IUserRepository

class UserRepository(
    private val iNetworkApi: INetworkApi
): IUserRepository{

    override fun getData(): DataModel{
        val prod = iNetworkApi.get()
        val data = DataModel(prod.something)
        return data
    }

}