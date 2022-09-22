package com.example.tagpropertyapp.domain.usecase

import com.example.tagpropertyapp.domain.model.Property
import com.example.tagpropertyapp.domain.repository.IPropertyRepository
import javax.inject.Inject

class SavePropertyUseCase @Inject constructor(
    private val propertyRepository: IPropertyRepository
) {
    suspend operator fun invoke(property: Property){
        propertyRepository.saveProperty(property)
    }
}