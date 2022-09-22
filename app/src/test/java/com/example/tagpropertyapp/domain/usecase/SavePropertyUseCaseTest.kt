@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.tagpropertyapp.domain.usecase

import com.example.tagpropertyapp.data.local.repository.PropertyRepository
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
class SavePropertyUseCaseTest {

    @Mock
    lateinit var propertyRepository: PropertyRepository

    private lateinit var savePropertyUseCase: SavePropertyUseCase

    @Before
    fun setUp() {
        savePropertyUseCase = SavePropertyUseCase(propertyRepository)
    }

    @Test
    fun `saveProperty should save property info`() = runTest{
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0,1"
        )
        savePropertyUseCase(property)
        verify(propertyRepository).saveProperty(property)
    }
}