package com.example.tagpropertyapp.data.local.repository

import com.example.tagpropertyapp.data.local.db.PropertyDao
import com.example.tagpropertyapp.data.local.mapper.PropertyToPropertyEntityMapper
import com.example.tagpropertyapp.domain.model.Property
import com.example.tagpropertyapp.domain.repository.IPropertyRepository
import javax.inject.Inject

class PropertyRepository @Inject constructor(
    private val propertyDao: PropertyDao,
    private val propertyToPropertyEntityMapper: PropertyToPropertyEntityMapper
): IPropertyRepository {

    override suspend fun saveProperty(property: Property) {
        propertyDao.insert(propertyToPropertyEntityMapper.transform(property))
    }
}