package com.example.tagpropertyapp.presentation.tag_property

import com.example.tagpropertyapp.common.DEFAULT_LOCATION
import com.google.android.gms.maps.model.LatLng

data class TagPropertyUIState(
    val propertyName: String = "",
    val markerPosition: LatLng = DEFAULT_LOCATION,
    val isMarkerAdded: Boolean = false,
    val isPropertySaved: Boolean = false
){
    val propertyCoordinates: String
    get() {
        return "${markerPosition.latitude},${markerPosition.longitude}"
    }

    val enableSubmit: Boolean
    get() {
        return propertyName.isNotEmpty()
    }

    companion object{
        val INITIAL_STATE = TagPropertyUIState()
    }
}
