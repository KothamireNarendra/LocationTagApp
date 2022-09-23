@file:OptIn(ExperimentalLifecycleComposeApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun Map(viewModel: TagPropertyViewModel) {
    val uiState = viewModel.tagPropertyUIStateFlow.collectAsStateWithLifecycle()
    Map(
        uiState = uiState.value,
        onLatLngChange = {
            viewModel.onPropertyCoordinatesChange(it)
        }
    )
}

@Composable
internal fun Map(
    uiState: TagPropertyUIState,
    onLatLngChange: (latLng: LatLng) -> Unit
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(uiState.markerPosition, 90f)
    }
    val properties = remember {
        mutableStateOf(MapProperties(mapType = MapType.SATELLITE))
    }

    val uiSettings = remember {
        mutableStateOf(MapUiSettings())
    }

    uiSettings.value = uiSettings.value.copy(scrollGesturesEnabled = !uiState.isMarkerAdded)

    val yPos: Float by animateFloatAsState(
        targetValue = if(uiState.isMarkerAdded) -250f else 0f,
        animationSpec = tween(300, easing = LinearOutSlowInEasing)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            translationY = yPos
        }
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .testTag("map"),
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