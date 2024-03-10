package com.michel.domain.usecase

import com.michel.domain.model.DataModel
import com.michel.domain.repository.IUserRepository

class GetDataUseCase(private val iUserRepository: IUserRepository) {
    fun execute() : DataModel {
        val dataModel = iUserRepository.getData()
        return dataModel;
    }

}