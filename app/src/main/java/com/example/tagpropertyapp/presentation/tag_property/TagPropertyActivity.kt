@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package com.example.tagpropertyapp.presentation.tag_property

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tagpropertyapp.presentation.theme.LocationTagAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TagPropertyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationTagAppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Color.Black)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: TagPropertyViewModel) {
    val uiState = viewModel.tagPropertyUIStateFlow.value
    val bottomSheetValue = if(uiState.isMarkerAdded) BottomSheetValue.Expanded else BottomSheetValue.Collapsed

    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(bottomSheetValue)
    )

    val addMarker = remember {
        mutableStateOf(false)
    }

    BackHandler {
        scope.launch {
            addMarker.value = false
            sheetState.bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetContent = {
            BottomSheet(viewModel = viewModel)
        },
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetGesturesEnabled = false,
        sheetPeekHeight = 38.dp,
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FAB(
                sheetState = sheetState,
                viewModel = viewModel
            )
        }
    ) {
        MapScreen(
            viewModel = viewModel
        )
    }
}



