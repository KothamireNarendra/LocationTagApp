package com.example.tagpropertyapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tagpropertyapp.data.local.model.PropertyEntity

@Database(entities = [PropertyEntity::class], version = 1)
abstract class PropertyDatabase: RoomDatabase() {
    abstract fun propertyDao(): PropertyDao
}