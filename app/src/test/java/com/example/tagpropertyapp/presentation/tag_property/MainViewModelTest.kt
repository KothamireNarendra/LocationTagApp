@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import com.example.tagpropertyapp.domain.model.Property
import com.example.tagpropertyapp.domain.usecase.SavePropertyUseCase
import com.example.tagpropertyapp.rules.CoroutineTestRule
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    lateinit var savePropertyUseCase: SavePropertyUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule = CoroutineTestRule(dispatcher)

    private lateinit var tagPropertyViewModel: TagPropertyViewModel

    @Before
    fun setUp() {
        tagPropertyViewModel = TagPropertyViewModel(savePropertyUseCase, dispatcher)
    }

    @Test
    fun `saveProperty should save property`() = runTest{
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0.0,1.0"
        )
        tagPropertyViewModel.tagPropertyUIStateFlow.first()
        tagPropertyViewModel.onPropertyNameChange(property.propertyName)
        tagPropertyViewModel.onPropertyCoordinatesChange(LatLng(0.0, 1.0))

        tagPropertyViewModel.saveProperty()
        verify(savePropertyUseCase).invoke(property)
    }

    @Test
    fun `addMarker should add marker`() = runTest{
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0.0,1.0"
        )
        val uiStates = mutableListOf<TagPropertyUIState>()
        val job = launch(dispatcher) {
            tagPropertyViewModel.tagPropertyUIStateFlow.toList(uiStates)
        }
        tagPropertyViewModel.onPropertyNameChange(property.propertyName)
        val latLng = LatLng(0.0, 1.0)
        tagPropertyViewModel.onPropertyCoordinatesChange(latLng)
        tagPropertyViewModel.addMarker()

        val expectedUiState = TagPropertyUIState(
            propertyName = property.propertyName,
            markerPosition = latLng,
            isMarkerAdded = true
        )

        assertEquals(expectedUiState, uiStates.last())
        job.cancel()
    }

    @Test
    fun `reset should remove marker and clear form data`() = runTest{
        val property = Property(
            propertyName = "property name",
            propertyCoordinates = "0.0,1.0"
        )
        val uiStates = mutableListOf<TagPropertyUIState>()
        val job = launch(dispatcher) {
            tagPropertyViewModel.tagPropertyUIStateFlow.toList(uiStates)
        }
        tagPropertyViewModel.onPropertyNameChange(property.propertyName)
        val latLng = LatLng(0.0, 1.0)
        tagPropertyViewModel.onPropertyCoordinatesChange(latLng)
        tagPropertyViewModel.addMarker()
        tagPropertyViewModel.reset()

        val expectedUiState = TagPropertyUIState(
            propertyName = "",
            markerPosition = latLng,
            isMarkerAdded = false
        )

        assertEquals(expectedUiState, uiStates.last())
        job.cancel()
    }
}