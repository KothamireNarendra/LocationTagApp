package com.example.tagpropertyapp.data.local.mapper

import com.example.tagpropertyapp.common.IMapper
import com.example.tagpropertyapp.data.local.model.PropertyEntity
import com.example.tagpropertyapp.domain.model.Property
import javax.inject.Inject

class PropertyToPropertyEntityMapper @Inject constructor() : IMapper<Property, PropertyEntity> {
    override fun transform(model: Property): PropertyEntity {
        return PropertyEntity(
            propertyName = model.propertyName,
            propertyCoordinates = model.propertyCoordinates
        )
    }
}