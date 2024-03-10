package com.michel.domain.repository

import com.michel.domain.model.DataModel

interface IUserRepository {

    fun getData(): DataModel

}