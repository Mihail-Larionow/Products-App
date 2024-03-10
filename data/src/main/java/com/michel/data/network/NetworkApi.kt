package com.michel.data.network

import com.michel.data.model.Data

class NetworkApi: INetworkApi {
    override fun get(): Data {
        val data = Data("some information")
        return data
    }
}