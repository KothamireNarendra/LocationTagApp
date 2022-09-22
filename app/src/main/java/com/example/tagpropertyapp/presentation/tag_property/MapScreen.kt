@file:OptIn(ExperimentalLifecycleComposeApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(viewModel: TagPropertyViewModel) {
    val uiState = viewModel.tagPropertyUIStateFlow.collectAsStateWithLifecycle()
    MapScreen(
        uiState = uiState.value,
        onLatLngChange = {
            viewModel.onPropertyCoordinatesChange(it)
        }
    )
}

@Composable
internal fun MapScreen(
    uiState: TagPropertyUIState,
    onLatLngChange: (latLng: LatLng) -> Unit
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.markerPosition, 20f)
    }
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }

    val uiSettings = remember {
        mutableStateOf(MapUiSettings())
    }

    uiSettings.value = uiSettings.value.copy(scrollGesturesEnabled = !uiState.isMarkerAdded)

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize().testTag("map"),
            properties = properties.value,
            uiSettings = uiSettings.value,
            cameraPositionState = cameraPositionState
        ) {
            val latLong = cameraPositionState.position.target
            onLatLngChange(latLong)

            if (uiState.isMarkerAdded) {
                Marker(
                    tag = "marker",
                    state = MarkerState(position = latLong),
                )
            }
        }

        if (!uiState.isMarkerAdded) {
            Icon(
                modifier = Modifier
                    .testTag("target")
                    .align(Alignment.Center)
                    .size(60.dp),
                tint = Color.Red,
                imageVector = Icons.Default.Add,
                contentDescription = "Location"
            )
        }
    }
}