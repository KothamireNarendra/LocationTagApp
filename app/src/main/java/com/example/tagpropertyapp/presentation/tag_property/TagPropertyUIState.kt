package com.example.tagpropertyapp.presentation.tag_property

import com.google.android.gms.maps.model.LatLng

data class TagPropertyUIState(
    val propertyName: String = "",
    val markerPosition: LatLng = LatLng(20.5937, 78.9629),
    val isMarkerAdded: Boolean = false
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
        //val DEFAULT_LOCATION = LatLng(20.5937, 78.9629)
    }
}
