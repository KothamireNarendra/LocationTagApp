@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.tagpropertyapp.data.local.repository

import com.example.tagpropertyapp.data.local.db.PropertyDao
import com.example.tagpropertyapp.data.local.mapper.PropertyToPropertyEntityMapper
import com.example.tagpropertyapp.domain.model.Property
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class PropertyRepositoryTest{

    @Mock
    lateinit var propertyDao: PropertyDao

    private val propertyToPropertyEntityMapper = PropertyToPropertyEntityMapper()
    private lateinit var propertyRepository: PropertyRepository

    @Before
    fun setUp(){
        propertyRepository = PropertyRepository(propertyDao, propertyToPropertyEntityMapper)
    }

    @Test
    fun `saveProperty should save property info in db`() = runTest{
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0,1"
        )
        propertyRepository.saveProperty(property)
        verify(propertyDao).insert(propertyToPropertyEntityMapper.transform(property))
    }
}