@file:OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterialApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch

@Composable
fun TagPropertyScreen(viewModel: TagPropertyViewModel) {
    val uiState = viewModel.tagPropertyUIStateFlow.collectAsStateWithLifecycle()
    val bottomSheetValue =
        if (uiState.value.isMarkerAdded) BottomSheetValue.Expanded else BottomSheetValue.Collapsed

    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(bottomSheetValue)
    )
    TagPropertyScreen(
        uiState = uiState.value,
        sheetState = sheetState,
        fab = {
            FAB(sheetState = sheetState, viewModel = viewModel)
        },
        bottomSheet = {
            BottomSheet(viewModel = viewModel)
        },
        map = {
            Map(viewModel = viewModel)
        },
        reset = viewModel::reset
    )
}


@Composable
internal fun TagPropertyScreen(
    uiState: TagPropertyUIState,
    sheetState: BottomSheetScaffoldState,
    fab: (@Composable () -> Unit),
    bottomSheet: (@Composable () -> Unit),
    map: (@Composable () -> Unit),
    reset: () -> Unit
) {
    val scope = rememberCoroutineScope()
    BackHandler {
        scope.launch {
            reset()
            sheetState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            bottomSheet()
        },
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetGesturesEnabled = false,
        sheetPeekHeight = 38.dp,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = fab
    ) {

        map()

        if (uiState.isPropertySaved) {
            reset()
            Toast.makeText(LocalContext.current, "Property is saved", Toast.LENGTH_LONG).show()
        }
    }
}

