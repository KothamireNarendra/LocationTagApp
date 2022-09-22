package com.example.tagpropertyapp.data.local.mapper

import com.example.tagpropertyapp.data.local.model.PropertyEntity
import com.example.tagpropertyapp.domain.model.Property
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PropertyToPropertyEntityMapperTest {

    private lateinit var propertyEntityMapper: PropertyToPropertyEntityMapper

    @Before
    fun setUp() {
        propertyEntityMapper = PropertyToPropertyEntityMapper()
    }

    @Test
    fun `transform should map property to propertyEntity model`(){
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0,1"
        )

        val propertyEntity = PropertyEntity(
            propertyName = property.propertyName,
            propertyCoordinates = property.propertyCoordinates
        )

        assertEquals(propertyEntity, propertyEntityMapper.transform(property))
    }

    @Test
    fun `transform should map properties to propertyEntities models`(){
        val property1 = Property(
            propertyName = "property name 1",
            propertyCoordinates = "0,1"
        )

        val property2 = Property(
            propertyName = "property name 2",
            propertyCoordinates = "0,2"
        )

        val properties = listOf(property1, property2)

        val propertyEntity1 = PropertyEntity(
            propertyName = property1.propertyName,
            propertyCoordinates = property1.propertyCoordinates
        )

        val propertyEntity2 = PropertyEntity(
            propertyName = property2.propertyName,
            propertyCoordinates = property2.propertyCoordinates
        )

        val propertyEntities = listOf(propertyEntity1, propertyEntity2)

        assertEquals(propertyEntities, propertyEntityMapper.transform(properties))
    }
}