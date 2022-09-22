package com.example.tagpropertyapp.presentation.tag_property

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tagpropertyapp.di.qualifier.IODispatcher
import com.example.tagpropertyapp.domain.model.Property
import com.example.tagpropertyapp.domain.usecase.SavePropertyUseCase
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TagPropertyViewModel @Inject constructor(
    private val savePropertyUseCase: SavePropertyUseCase,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val propertyNameFlow = MutableStateFlow("")
    private val propertyCoordinatesFlow = MutableStateFlow(LatLng(20.5937, 78.9629))
    private val addMarkerFlow = MutableStateFlow(false)

    fun onPropertyNameChange(propertyName: String) {
        propertyNameFlow.value = propertyName
    }

    fun onPropertyCoordinatesChange(latLng: LatLng) {
        propertyCoordinatesFlow.value = latLng
    }

    fun addMarker(){
        addMarkerFlow.value = true
    }

    fun reset(){
        addMarkerFlow.value = false
        propertyNameFlow.value = ""
    }

    val tagPropertyUIStateFlow: StateFlow<TagPropertyUIState> =
        combine(
            propertyNameFlow,
            propertyCoordinatesFlow,
            addMarkerFlow
        ) { propertyName, propertyCoordinates, addMarker ->
            TagPropertyUIState(
                propertyName = propertyName,
                markerPosition = propertyCoordinates,
                isMarkerAdded = addMarker
            )
        }.stateIn(
            initialValue = TagPropertyUIState.INITIAL_STATE,
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )


    fun saveProperty(){
        viewModelScope.launch {
            val state = tagPropertyUIStateFlow.value
            withContext(dispatcher){
                savePropertyUseCase(
                    Property(
                        propertyName = state.propertyName,
                        propertyCoordinates = state.propertyCoordinates
                    )
                )
            }
        }
    }
}