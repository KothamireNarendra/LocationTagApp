@file:OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch


@Composable
fun FAB(
    sheetState: BottomSheetScaffoldState,
    viewModel: TagPropertyViewModel
){
    val uiState = viewModel.tagPropertyUIStateFlow.collectAsStateWithLifecycle()
    FAB(
        sheetState = sheetState,
        uiState = uiState.value,
        addMarker = viewModel::addMarker,
        removeMarker = viewModel::reset
    )
}

@Composable
internal fun FAB(
    sheetState: BottomSheetScaffoldState,
    uiState: TagPropertyUIState,
    addMarker: () -> Unit,
    removeMarker: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    FloatingActionButton(onClick = {
        scope.launch {
            if (sheetState.bottomSheetState.isCollapsed) {
                addMarker()
                sheetState.bottomSheetState.expand()
            } else {
                removeMarker()
                sheetState.bottomSheetState.collapse()
                focusManager.clearFocus()
            }
        }
    }) {
        val icon = if (!uiState.isMarkerAdded) {
            Icons.Default.Add
        } else {
            Icons.Default.Close
        }
        Icon(
            imageVector = icon,
            contentDescription = "Tag Location"
        )
    }
}
