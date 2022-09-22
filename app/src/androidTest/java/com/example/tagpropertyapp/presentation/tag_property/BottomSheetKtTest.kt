package com.example.tagpropertyapp.presentation.tag_property

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class BottomSheetKtTest{

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun propertyCoordinatesTextFieldIsNotEnabledForEnteringText(){
        composeTestRule.setContent {
            BottomSheet(
                uiState = TagPropertyUIState(
                    propertyName = "property name"
                ),
                onPropertyNameChange = {}) {
            }
        }
        composeTestRule
            .onNodeWithTag("propertyCoordinates textField")
            .assertIsNotEnabled()
    }

    @Test
    fun propertyCoordinatesTextFieldShouldShowLatLngAsCommaSeparatedText(){
        composeTestRule.setContent {
            BottomSheet(
                uiState = TagPropertyUIState(
                    markerPosition = LatLng(1.0, 2.0)
                ),
                onPropertyNameChange = {}) {
            }
        }
        composeTestRule
            .onNodeWithTag("propertyCoordinates textField")
            .assert(hasText("1.0,2.0"))
    }

    @Test
    fun onEnteringPropertyNameShouldEnableSubmitButton(){
        composeTestRule.setContent {
            BottomSheet(
                uiState = TagPropertyUIState(
                    propertyName = "property name"
                ),
                onPropertyNameChange = {}) {
            }
        }
        composeTestRule
            .onNodeWithTag("submit button")
            .assertIsEnabled()
    }

    @Test
    fun onEmptyingPropertyNameShouldDisableSubmitButton(){
        composeTestRule.setContent {
            BottomSheet(
                uiState = TagPropertyUIState(
                    propertyName = ""
                ),
                onPropertyNameChange = {}) {
            }
        }
        composeTestRule
            .onNodeWithTag("submit button")
            .assertIsNotEnabled()
    }
}