package com.example.tagpropertyapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property")
data class PropertyEntity(
    @PrimaryKey(autoGenerate = true)
    val propertyId: Int = 0,

    @ColumnInfo(name = "property_name")
    val propertyName: String,

    @ColumnInfo(name = "property_coordinates")
    val propertyCoordinates: String
)
