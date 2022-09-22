package com.example.tagpropertyapp.domain.repository

import com.example.tagpropertyapp.domain.model.Property

interface IPropertyRepository {

    suspend fun saveProperty(property: Property)
}